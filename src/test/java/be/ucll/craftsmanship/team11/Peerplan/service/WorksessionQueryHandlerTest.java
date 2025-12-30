package be.ucll.craftsmanship.team11.Peerplan.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import be.ucll.craftsmanship.team11.PeerPlan.planning.domain.valueObjects.CollaboratorId;
import be.ucll.craftsmanship.team11.PeerPlan.planning.domain.valueObjects.TimeRange;
import be.ucll.craftsmanship.team11.PeerPlan.task.domain.model.valueObjects.TaskId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import be.ucll.craftsmanship.team11.PeerPlan.planning.application.WorksessionQueryHandler;
import be.ucll.craftsmanship.team11.PeerPlan.planning.domain.Worksession;
import be.ucll.craftsmanship.team11.PeerPlan.planning.domain.valueObjects.WorksessionId;
import be.ucll.craftsmanship.team11.PeerPlan.planning.infrastructure.WorksessionRepository;
import be.ucll.craftsmanship.team11.PeerPlan.planning.queries.FindAllWorksessionsQuery;
import be.ucll.craftsmanship.team11.PeerPlan.planning.queries.GetWorksessionByIdQuery;
import be.ucll.craftsmanship.team11.PeerPlan.shared.exception.NotFoundException;

@ExtendWith(MockitoExtension.class)
class WorksessionQueryHandlerTest {

    @Mock
    private WorksessionRepository worksessionRepository;

    @InjectMocks
    private WorksessionQueryHandler handler;

    private Worksession session1;
    private Worksession session2;

    @BeforeEach
    void setup() {
        session1 = new Worksession(
                "Session 1",
                new TimeRange(
                        LocalDateTime.of(2025, 12, 14, 10, 0),
                        LocalDateTime.of(2025, 12, 14, 11, 0)),
                new TaskId(UUID.randomUUID()),
                new CollaboratorId(UUID.randomUUID())
        );
        session1.setId(new WorksessionId());

        session2 = new Worksession(
                "Session 2",
                new TimeRange(
                        LocalDateTime.of(2025, 12, 14, 12, 0),
                        LocalDateTime.of(2025, 12, 14, 13, 0)),
                new TaskId(UUID.randomUUID()),
                new CollaboratorId(UUID.randomUUID())
        );
        session2.setId(new WorksessionId());
    }

    @Test
    void shouldReturnAllWorksessions() {
        when(worksessionRepository.findAll()).thenReturn(List.of(session1, session2));

        List<Worksession> result = handler.handle(new FindAllWorksessionsQuery());

        assertThat(result).containsExactlyInAnyOrder(session1, session2);
        verify(worksessionRepository).findAll();
    }

    @Test
    void shouldReturnWorksessionById() {
        when(worksessionRepository.findById(session1.getId())).thenReturn(Optional.of(session1));

        Worksession result = handler.handle(new GetWorksessionByIdQuery(session1.getId()));

        assertThat(result).isEqualTo(session1);
        verify(worksessionRepository).findById(session1.getId());
    }

    @Test
    void shouldThrowWhenWorksessionNotFound() {
        WorksessionId missingId = new WorksessionId();
        when(worksessionRepository.findById(missingId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> handler.handle(new GetWorksessionByIdQuery(missingId)))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Worksession not found");

        verify(worksessionRepository).findById(missingId);
    }
}
