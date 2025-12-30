package be.ucll.craftsmanship.team11.Peerplan.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import be.ucll.craftsmanship.team11.PeerPlan.planning.domain.valueObjects.TimeRange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import be.ucll.craftsmanship.team11.PeerPlan.collaboration.infrastructure.GroupRepository;
import be.ucll.craftsmanship.team11.PeerPlan.identity.domain.valueObjects.UserId;
import be.ucll.craftsmanship.team11.PeerPlan.identity.infrastructure.UserRepository;
import be.ucll.craftsmanship.team11.PeerPlan.planning.application.WorksessionCommandHandler;
import be.ucll.craftsmanship.team11.PeerPlan.planning.commands.CreateWorksessionCommand;
import be.ucll.craftsmanship.team11.PeerPlan.planning.commands.DeleteWorksessionCommand;
import be.ucll.craftsmanship.team11.PeerPlan.planning.commands.UpdateWorksessionCommand;
import be.ucll.craftsmanship.team11.PeerPlan.planning.domain.Worksession;
import be.ucll.craftsmanship.team11.PeerPlan.planning.domain.valueObjects.CollaboratorId;
import be.ucll.craftsmanship.team11.PeerPlan.planning.domain.valueObjects.WorksessionId;
import be.ucll.craftsmanship.team11.PeerPlan.planning.infrastructure.WorksessionRepository;
import be.ucll.craftsmanship.team11.PeerPlan.shared.exception.NotFoundException;
import be.ucll.craftsmanship.team11.PeerPlan.shared.exception.ServiceException;
import be.ucll.craftsmanship.team11.PeerPlan.task.domain.model.valueObjects.TaskId;
import be.ucll.craftsmanship.team11.PeerPlan.task.infrastructure.TaskRepository;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WorksessionCommandHandlerTest {

    @Mock
    private WorksessionRepository worksessionRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private GroupRepository groupRepository;
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private WorksessionCommandHandler handler;

    private CollaboratorId userCollaborator;
    private TaskId taskSubject;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @BeforeEach
    void setUp() {
        UUID userId = UUID.randomUUID();
        UUID taskId = UUID.randomUUID();
        UUID subtaskId = UUID.randomUUID();

        userCollaborator = new CollaboratorId(userId);
        taskSubject = new TaskId(taskId);

        startTime = LocalDateTime.of(2025, 1, 1, 10, 0);
        endTime = LocalDateTime.of(2025, 1, 1, 11, 0);
    }

    @Test
    void shouldCreateWorksessionForUser() {
        when(worksessionRepository
                .existsByTimeRange_StartTimeLessThanAndTimeRange_EndTimeGreaterThan(
                        endTime, startTime
                ))
                .thenReturn(false);

        when(taskRepository.existsById(any(TaskId.class)))
                .thenReturn(true);

        when(worksessionRepository.save(any()))
                .thenAnswer(invocation -> {
                    Worksession ws = invocation.getArgument(0);
                    ws.setId(new WorksessionId());
                    return ws;
                });

        Worksession result = handler.handle(new CreateWorksessionCommand(
                "Study",
                new TimeRange(startTime, endTime),
                taskSubject,
                userCollaborator
        ));

        assertThat(result.getId()).isNotNull();
        assertThat(result.getCollaboratorId()).isEqualTo(userCollaborator);
    }

    @Test
    void shouldFailIfTimeRangeInvalid() {
        assertThatThrownBy(() ->
                new TimeRange(endTime, startTime)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldFailIfSubjectNotFound() {
        when(worksessionRepository
                .existsByTimeRange_StartTimeLessThanAndTimeRange_EndTimeGreaterThan(
                        endTime, startTime
                ))
                .thenReturn(false);

        when(taskRepository.existsById(any(TaskId.class))).thenReturn(false);
        when(taskRepository.existsBySubtasksId(any())).thenReturn(false);

        assertThatThrownBy(() -> handler.handle(new CreateWorksessionCommand(
                "Unknown task",
                new TimeRange(startTime, endTime),
                taskSubject,
                userCollaborator
        )))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void shouldFailIfOverlappingWorksession() {
        when(worksessionRepository
                .existsByTimeRange_StartTimeLessThanAndTimeRange_EndTimeGreaterThan(
                        endTime.plusMinutes(30),
                        startTime.plusMinutes(30)
                ))
                .thenReturn(true);

        assertThatThrownBy(() -> handler.handle(new CreateWorksessionCommand(
                "New",
                new TimeRange(startTime.plusMinutes(30), endTime.plusMinutes(30)),
                taskSubject,
                userCollaborator
        )))
                .isInstanceOf(ServiceException.class);
    }

    @Test
    void shouldUpdateWorksessionSuccessfully() {
        Worksession existing = new Worksession(
                "Old",
                new TimeRange(startTime, endTime),
                taskSubject,
                userCollaborator
        );
        existing.setId(new WorksessionId());

        when(worksessionRepository.findById(existing.getId()))
                .thenReturn(Optional.of(existing));

        when(worksessionRepository
                .existsByTimeRange_StartTimeLessThanAndTimeRange_EndTimeGreaterThan(
                        endTime.plusMinutes(10),
                        startTime.plusMinutes(10)
                ))
                .thenReturn(false);

        when(taskRepository.existsById(any(TaskId.class))).thenReturn(true);

        when(worksessionRepository.save(any()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Worksession result = handler.handle(
                existing.getId(),
                new UpdateWorksessionCommand(
                        "Updated",
                        new TimeRange(
                                startTime.plusMinutes(10),
                                endTime.plusMinutes(10)
                        ),
                        taskSubject
                )
        );

        assertThat(result.getTitle()).isEqualTo("Updated");
        assertThat(result.getCollaboratorId()).isEqualTo(userCollaborator);
    }

    @Test
    void shouldFailUpdateIfNotFound() {
        UpdateWorksessionCommand command = new UpdateWorksessionCommand(
            "Updated",
            new TimeRange(
                startTime,
                endTime),
            taskSubject
        );

        WorksessionId nonExistentId = new WorksessionId();
        when(worksessionRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> handler.handle(nonExistentId, command))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Worksession not found");
    }


    @Test
    void shouldDeleteExistingWorksession() {
        WorksessionId wsId = new WorksessionId();
        when(worksessionRepository.existsById(wsId)).thenReturn(true);

        handler.handle(new DeleteWorksessionCommand(wsId));

        verify(worksessionRepository).deleteById(wsId);
    }

    @Test
    void shouldFailDeletingNonExistingWorksession() {
        WorksessionId wsId = new WorksessionId();
        when(worksessionRepository.existsById(wsId)).thenReturn(false);

        assertThatThrownBy(() -> handler.handle(new DeleteWorksessionCommand(wsId)))
                .isInstanceOf(NotFoundException.class);

        verify(worksessionRepository, never()).deleteById(any());
    }
}