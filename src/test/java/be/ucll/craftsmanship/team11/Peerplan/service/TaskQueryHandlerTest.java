package be.ucll.craftsmanship.team11.Peerplan.service;

import be.ucll.craftsmanship.team11.PeerPlan.identity.domain.valueObjects.UserId;
import be.ucll.craftsmanship.team11.PeerPlan.task.application.TaskQueryHandler;
import be.ucll.craftsmanship.team11.PeerPlan.task.domain.model.Task;
import be.ucll.craftsmanship.team11.PeerPlan.task.infrastructure.TaskRepository;
import be.ucll.craftsmanship.team11.PeerPlan.task.queries.TaskQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskQueryHandlerTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskQueryHandler handler;

    private Task task1;
    private Task task2;
    private UserId assigneeId;

    @BeforeEach
    void setup() {
        assigneeId = new UserId(UUID.randomUUID());
        task1 = new Task("Task 1", assigneeId);
        task2 = new Task("Task 2", new UserId(UUID.randomUUID()));
    }

    @Test
    void shouldReturnAllTasks() {
        when(taskRepository.findAll()).thenReturn(List.of(task1, task2));

        List<Task> result = handler.findAll();

        assertThat(result).containsExactlyInAnyOrder(task1, task2);
        verify(taskRepository).findAll();
    }

    @Test
    void shouldFilterTasksByAssignee() {
        TaskQuery query = new TaskQuery(assigneeId);
        when(taskRepository.findByAssignee(assigneeId)).thenReturn(List.of(task1));

        List<Task> result = handler.filterTasks(query);

        assertThat(result).containsExactly(task1);
        verify(taskRepository).findByAssignee(assigneeId);
        verify(taskRepository, never()).findAll();
    }

    @Test
    void shouldReturnAllTasksWhenAssigneeIsNull() {
        TaskQuery query = new TaskQuery(null);
        when(taskRepository.findAll()).thenReturn(List.of(task1, task2));

        List<Task> result = handler.filterTasks(query);

        assertThat(result).containsExactlyInAnyOrder(task1, task2);
        verify(taskRepository).findAll();
        verify(taskRepository, never()).findByAssignee(any());
    }
}
