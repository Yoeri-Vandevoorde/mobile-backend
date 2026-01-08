package be.ucll.craftsmanship.team11.PeerPlan.task.infrastructure;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import be.ucll.craftsmanship.team11.PeerPlan.identity.infrastructure.UserRepository;
import be.ucll.craftsmanship.team11.PeerPlan.task.domain.model.Task;
import lombok.RequiredArgsConstructor;

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

        var mainUser = users.get(0);

        // Task 1: Complete Database Assignment
        Task task1 = new Task(
                "Complete Database Assignment",
                mainUser.getId()
        );
        taskRepository.save(task1);

        // Task 2: Study for Algorithms Exam
        Task task2 = new Task(
                "Study for Algorithms Exam",
                mainUser.getId()
        );
        taskRepository.save(task2);

        // Task 3: Submit Project Proposal
        Task task3 = new Task(
                "Submit Project Proposal",
                mainUser.getId()
        );
        taskRepository.save(task3);

        // Task 4: Read Chapter 5-7
        Task task4 = new Task(
                "Read Chapter 5-7",
                mainUser.getId()
        );
        taskRepository.save(task4);

        // Task 5: Group Presentation Prep
        Task task5 = new Task(
                "Group Presentation Prep",
                mainUser.getId()
        );
        taskRepository.save(task5);

        System.out.println(">>> Database initialized with 5 tasks for the main user");
    }
}
