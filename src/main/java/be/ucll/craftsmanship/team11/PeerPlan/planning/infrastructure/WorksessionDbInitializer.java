package be.ucll.craftsmanship.team11.PeerPlan.planning.infrastructure;

import java.time.LocalDateTime;

import be.ucll.craftsmanship.team11.PeerPlan.planning.domain.valueObjects.TimeRange;
import be.ucll.craftsmanship.team11.PeerPlan.task.domain.model.valueObjects.TaskId;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import be.ucll.craftsmanship.team11.PeerPlan.identity.infrastructure.UserRepository;
import be.ucll.craftsmanship.team11.PeerPlan.collaboration.infrastructure.GroupRepository;
import be.ucll.craftsmanship.team11.PeerPlan.planning.domain.Worksession;
import be.ucll.craftsmanship.team11.PeerPlan.planning.domain.valueObjects.CollaboratorId;
import be.ucll.craftsmanship.team11.PeerPlan.planning.domain.valueObjects.WorksessionId;
import be.ucll.craftsmanship.team11.PeerPlan.task.infrastructure.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
@Order(4)
@Transactional
public class WorksessionDbInitializer implements CommandLineRunner {

    private final WorksessionRepository worksessionRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final TaskRepository taskRepository;

    @Override
    public void run(String... args) {
        worksessionRepository.deleteAll();

        var users = userRepository.findAll();
        var tasks = taskRepository.findAll();
        var group = groupRepository.findAll().stream().findFirst().orElse(null);

        if (users.isEmpty() || tasks.isEmpty() || group == null) return;

        // Worksession for individual user
        Worksession wsUser = new Worksession(
                "User Planning Session",
                new TimeRange(
                        LocalDateTime.now().plusDays(1),
                        LocalDateTime.now().plusDays(1).plusHours(2)
                ),
                new TaskId(tasks.get(0).getId().value()),
                new CollaboratorId(users.get(0).getId().id())
        );
        wsUser.setId(new WorksessionId()); // explicitly generate ID
        worksessionRepository.save(wsUser);

        System.out.println(">>> Database initialized with a user and a group worksession");
    }
}
