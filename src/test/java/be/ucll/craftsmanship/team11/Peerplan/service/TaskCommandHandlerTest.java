package be.ucll.craftsmanship.team11.Peerplan.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import be.ucll.craftsmanship.team11.PeerPlan.identity.domain.valueObjects.UserId;
import be.ucll.craftsmanship.team11.PeerPlan.task.application.TaskCommandHandler;
import be.ucll.craftsmanship.team11.PeerPlan.task.commands.AddSubtaskCommand;
import be.ucll.craftsmanship.team11.PeerPlan.task.commands.CreateTaskCommand;
import be.ucll.craftsmanship.team11.PeerPlan.task.commands.DeleteTaskCommand;
import be.ucll.craftsmanship.team11.PeerPlan.task.commands.RemoveSubtaskCommand;
import be.ucll.craftsmanship.team11.PeerPlan.task.domain.model.Subtask;
import be.ucll.craftsmanship.team11.PeerPlan.task.domain.model.Task;
import be.ucll.craftsmanship.team11.PeerPlan.task.domain.model.valueObjects.SubtaskId;
import be.ucll.craftsmanship.team11.PeerPlan.task.domain.model.valueObjects.TaskId;
import be.ucll.craftsmanship.team11.PeerPlan.task.infrastructure.TaskRepository;

@ExtendWith(MockitoExtension.class)
class TaskCommandHandlerTest {

    @Mock
    TaskRepository taskRepository;

    @InjectMocks
    TaskCommandHandler handler;

    @Test
    void shouldCreateTask() {
        UserId creatorId = new UserId(UUID.randomUUID());
        String title = "Implement feature";

        CreateTaskCommand command = new CreateTaskCommand(title, creatorId);

        Task savedTask = new Task(title, creatorId);
        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        Task result = handler.handle(command);

        assertThat(result.getTitle()).isEqualTo(title);

        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void shouldDeleteTask() {
        TaskId taskId = new TaskId(UUID.randomUUID());
        UserId creatorId = new UserId(UUID.randomUUID());
        Task task = new Task("Test task", creatorId);

        when(taskRepository.findById(taskId))
                .thenReturn(Optional.of(task));

        DeleteTaskCommand command = new DeleteTaskCommand(taskId);

        boolean result = handler.handle(command);

        assertThat(result).isTrue();
        verify(taskRepository).delete(task);
    }

    @Test
    void deletingNonExistingTaskShouldFail() {
        TaskId taskId = new TaskId(UUID.randomUUID());

        when(taskRepository.findById(taskId))
                .thenReturn(Optional.empty());

        DeleteTaskCommand command = new DeleteTaskCommand(taskId);

        assertThatThrownBy(() -> handler.handle(command))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Task not found");
    }

    @Test
    void shouldAddSubtask() {
        TaskId taskId = new TaskId(UUID.randomUUID());
        UserId creatorId = new UserId(UUID.randomUUID());

        Task task = new Task("Main task", creatorId);

        when(taskRepository.findById(taskId))
                .thenReturn(Optional.of(task));

        AddSubtaskCommand command = new AddSubtaskCommand(taskId, "Subtask 1");

        handler.handle(command);

        assertThat(task.getSubtasks())
                .hasSize(1)
                .anyMatch(s -> s.getTitle().equals("Subtask 1"));

        verify(taskRepository).save(task);
    }

    @Test
    void shouldRemoveSubtask() {
        TaskId taskId = new TaskId(UUID.randomUUID());
        UserId creatorId = new UserId(UUID.randomUUID());
        Task task = new Task("Main task", creatorId);
        Subtask subtask = new Subtask("Subtask 1");

        task.addSubtask(subtask);

        when(taskRepository.findById(taskId))
                .thenReturn(Optional.of(task));

        RemoveSubtaskCommand command = new RemoveSubtaskCommand(taskId, subtask.getId());

        handler.handle(command);

        assertThat(task.getSubtasks()).isEmpty();
        verify(taskRepository).save(task);
    }

    @Test
    void removingNonExistingSubtaskShouldDoNothing() {
        TaskId taskId = new TaskId(UUID.randomUUID());
        UserId creatorId = new UserId(UUID.randomUUID());
        Task task = new Task("Main task", creatorId);

        when(taskRepository.findById(taskId))
                .thenReturn(Optional.of(task));

        RemoveSubtaskCommand command = new RemoveSubtaskCommand(taskId, new SubtaskId(UUID.randomUUID()));

        handler.handle(command);

        assertThat(task.getSubtasks()).isEmpty();
        verify(taskRepository).save(task);
    }
}
