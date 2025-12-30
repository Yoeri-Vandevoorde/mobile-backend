package be.ucll.craftsmanship.team11.PeerPlan.task.infrastructure;

import be.ucll.craftsmanship.team11.PeerPlan.identity.infrastructure.UserRepository;
import be.ucll.craftsmanship.team11.PeerPlan.task.domain.model.Subtask;
import be.ucll.craftsmanship.team11.PeerPlan.task.domain.model.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(3)
public class DbInitializer implements CommandLineRunner {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Override
    public void run(String... args) {
        taskRepository.deleteAll();

        var users = userRepository.findAll();
        if (users.isEmpty()) return;

        // Task for individual user
        Task userTask = new Task(
                "User Task",
                users.get(0).getId() // assign to a single user
        );
        userTask.addSubtask(new Subtask("User Subtask 1"));
        userTask.addSubtask(new Subtask("User Subtask 2"));

        taskRepository.save(userTask);

        System.out.println(">>> Database initialized with a task (2 subtasks) for a user");
    }
}
