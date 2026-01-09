package be.ucll.craftsmanship.team11.PeerPlan.task.infrastructure;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import be.ucll.craftsmanship.team11.PeerPlan.identity.infrastructure.UserRepository;
import be.ucll.craftsmanship.team11.PeerPlan.task.domain.model.Subtask;
import be.ucll.craftsmanship.team11.PeerPlan.task.domain.model.Task;
import be.ucll.craftsmanship.team11.PeerPlan.task.domain.model.valueObjects.Status;
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
        Subtask subtask11 = new Subtask("Create database", Status.DONE);
        Subtask subtask12 = new Subtask("Drop database", Status.TODO);
        task1.addSubtask(subtask11);
        task1.addSubtask(subtask12);
        taskRepository.save(task1);

        // Task 2: Study for Algorithms Exam
        Task task2 = new Task(
                "Study for Algorithms Exam",
                mainUser.getId()
        );
                Subtask subtask21 = new Subtask("Study", Status.DONE);
        Subtask subtask22 = new Subtask("Do mock exam", Status.TODO);
        task2.addSubtask(subtask21);
        task2.addSubtask(subtask22);
        taskRepository.save(task2);

        // Task 3: Submit Project Proposal
        Task task3 = new Task(
                "Submit Project Proposal",
                mainUser.getId()
        );
        Subtask subtask31 = new Subtask("Log in to company portal", Status.TODO);
        Subtask subtask32 = new Subtask("Submit proposal", Status.TODO);
        task3.addSubtask(subtask31);
        task3.addSubtask(subtask32);
        taskRepository.save(task3);

        // Task 4: Read Chapter 5-7
        Task task4 = new Task(
                "Read Chapter 5-7",
                mainUser.getId()
        );
        Subtask subtask41 = new Subtask("Read Chapter 5", Status.DONE);
        Subtask subtask42 = new Subtask("Read Chapter 6", Status.TODO);
        Subtask subtask43 = new Subtask("Read Chapter 7", Status.TODO);
        task4.addSubtask(subtask41);
        task4.addSubtask(subtask42);
        task4.addSubtask(subtask43);
        taskRepository.save(task4);

        // Task 5: Group Presentation Prep
        Task task5 = new Task(
                "Group Presentation Prep",
                mainUser.getId()
        );
        Subtask subtask51 = new Subtask("Create slides", Status.DONE);
        Subtask subtask52 = new Subtask("Prepare script", Status.TODO);
        task5.addSubtask(subtask51);
        task5.addSubtask(subtask52);
        taskRepository.save(task5);

        System.out.println(">>> Database initialized with 5 tasks for the main user");
    }
}
