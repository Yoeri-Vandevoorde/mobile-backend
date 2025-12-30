package be.ucll.craftsmanship.team11.PeerPlan.task.controllers;
import be.ucll.craftsmanship.team11.PeerPlan.task.application.TaskCommandHandler;
import be.ucll.craftsmanship.team11.PeerPlan.task.application.TaskQueryHandler;
import be.ucll.craftsmanship.team11.PeerPlan.task.commands.AddSubtaskCommand;
import be.ucll.craftsmanship.team11.PeerPlan.task.commands.CreateTaskCommand;
import be.ucll.craftsmanship.team11.PeerPlan.task.commands.DeleteTaskCommand;
import be.ucll.craftsmanship.team11.PeerPlan.task.commands.RemoveSubtaskCommand;
import be.ucll.craftsmanship.team11.PeerPlan.task.queries.TaskQuery;
import jakarta.validation.Valid;
import be.ucll.craftsmanship.team11.PeerPlan.task.domain.model.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskCommandHandler commandHandler;
    private final TaskQueryHandler queryHandler;

    @GetMapping
    public List<Task> getAllTasks() {
        return queryHandler.findAll();
    }

    @PostMapping("/filter")
    public List<Task> filterTasks(@RequestBody TaskQuery query) {
        return queryHandler.filterTasks(query);
    }

    @PostMapping
    public Task addTask(@RequestBody @Valid CreateTaskCommand command) {
        return commandHandler.handle(command);
    }

    @PostMapping("/subtask")
    public void addSubtask(@RequestBody @Valid AddSubtaskCommand command) {
        commandHandler.handle(command);
    }

    @DeleteMapping("/subtask")
    public void deleteSubtask(@RequestBody @Valid RemoveSubtaskCommand command) {
        commandHandler.handle(command);
    }

    @DeleteMapping
    public void deleteTask(@RequestBody @Valid DeleteTaskCommand command) {
        commandHandler.handle(command);
    }
}


