package be.ucll.craftsmanship.team11.PeerPlan.task.application;

import org.springframework.stereotype.Service;

import be.ucll.craftsmanship.team11.PeerPlan.task.commands.AddSubtaskCommand;
import be.ucll.craftsmanship.team11.PeerPlan.task.commands.CreateTaskCommand;
import be.ucll.craftsmanship.team11.PeerPlan.task.commands.DeleteTaskCommand;
import be.ucll.craftsmanship.team11.PeerPlan.task.commands.RemoveSubtaskCommand;
import be.ucll.craftsmanship.team11.PeerPlan.task.commands.UpdateTaskCommand;
import be.ucll.craftsmanship.team11.PeerPlan.task.domain.model.Subtask;
import be.ucll.craftsmanship.team11.PeerPlan.task.domain.model.Task;
import be.ucll.craftsmanship.team11.PeerPlan.task.infrastructure.TaskRepository;
import jakarta.transaction.Transactional;

@Service
public class TaskCommandHandler {

    private final TaskRepository taskRepository;

    public TaskCommandHandler(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Transactional
    public Task handle(CreateTaskCommand command) {
        Task task = new Task(command.title(), command.creatorId());
        return taskRepository.save(task);
    }

    @Transactional
    public boolean handle(DeleteTaskCommand command) {
        Task task = taskRepository.findById(command.taskId())
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));
        taskRepository.delete(task);
        return true;
    }

    @Transactional
    public void handle(AddSubtaskCommand command) {
        Task task = taskRepository.findById(command.taskId())
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));
        task.addSubtask(new Subtask(command.title()));
        taskRepository.save(task);
    }

    @Transactional
    public void handle(RemoveSubtaskCommand command) {
        Task task = taskRepository.findById(command.taskId())
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        task.findSubtaskById(command.subtaskId())
                .ifPresent(task::removeSubtask);

        taskRepository.save(task);
    }

    @Transactional
    public Task handle(UpdateTaskCommand command) {
        Task task = taskRepository.findById(command.getId())
            .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        task.setTitle(command.getTitle());
        task.setStatus(command.getStatus());

        // Update subtasks
        command.getSubtasks().forEach(s -> {
            Subtask sub = task.findSubtaskById(s.getId())
                .orElseThrow(() -> new IllegalArgumentException("Subtask not found"));
            sub.setTitle(s.getTitle());
            sub.setStatus(s.getStatus());
        });

        return taskRepository.save(task);
    }
}
