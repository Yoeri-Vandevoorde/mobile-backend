package be.ucll.craftsmanship.team11.PeerPlan.planning.infrastructure;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import be.ucll.craftsmanship.team11.PeerPlan.collaboration.domain.valueObjects.GroupId;
import be.ucll.craftsmanship.team11.PeerPlan.collaboration.infrastructure.GroupRepository;
import be.ucll.craftsmanship.team11.PeerPlan.identity.domain.valueObjects.UserId;
import be.ucll.craftsmanship.team11.PeerPlan.identity.infrastructure.UserRepository;
import be.ucll.craftsmanship.team11.PeerPlan.planning.domain.Worksession;
import be.ucll.craftsmanship.team11.PeerPlan.planning.domain.valueObjects.CollaboratorId;
import be.ucll.craftsmanship.team11.PeerPlan.planning.domain.valueObjects.TimeRange;
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
        var groups = groupRepository.findAll();

        if (users.size() < 8 || tasks.isEmpty() || groups.isEmpty()) return;

        var mainUser = users.get(0);
        var task1 = tasks.get(0);

        // User's sessions (Week 1: Dec 23-29)
        createWorksession("Database Design", "2025-12-23T09:00:00", 2, mainUser.getId());
        createWorksession("React Native Study", "2025-12-23T14:00:00", 2, mainUser.getId());
        createWorksession("Algorithm Practice", "2025-12-24T10:00:00", 2, mainUser.getId());
        createWorksession("Project Meeting", "2025-12-25T15:00:00", 1.5f, mainUser.getId());
        createWorksession("Code Review", "2025-12-26T11:00:00", 1.5f, mainUser.getId());
        createWorksession("Weekend Study Session", "2025-12-28T10:00:00", 3, mainUser.getId());
        createWorksession("Personal Project", "2025-12-29T14:00:00", 3, mainUser.getId());

        // User's sessions (Week 2: Dec 30 - Jan 5) - CURRENT WEEK
        createWorksession("Sprint Planning", "2025-12-30T09:00:00", 1.5f, mainUser.getId());
        createWorksession("Backend Development", "2025-12-31T13:00:00", 3, mainUser.getId());
        createWorksession("Testing & QA", "2026-01-01T10:00:00", 2, mainUser.getId());
        createWorksession("Documentation", "2026-01-02T14:00:00", 2, mainUser.getId());
        createWorksession("Team Sync", "2026-01-03T11:00:00", 1, mainUser.getId());
        createWorksession("Hackathon Prep", "2026-01-04T09:00:00", 9, mainUser.getId());

        // User's sessions (Week 3: Jan 6-12)
        createWorksession("Exam Preparation", "2026-01-06T08:00:00", 4, mainUser.getId());
        createWorksession("Study Group", "2026-01-07T14:00:00", 3, mainUser.getId());
        createWorksession("Practice Problems", "2026-01-08T10:00:00", 3, mainUser.getId());
        createWorksession("Mock Exam", "2026-01-09T09:00:00", 3, mainUser.getId());
        createWorksession("Final Review", "2026-01-10T13:00:00", 3, mainUser.getId());
        createWorksession("Relaxation Day", "2026-01-11T10:00:00", 2, mainUser.getId());

        // Peer sessions (Emma Johnson - p1)
        createWorksession("Linear Algebra Study", "2025-12-23T10:00:00", 2, users.get(1).getId());
        createWorksession("Database Assignment", "2025-12-24T09:00:00", 2, users.get(1).getId());
        createWorksession("Python Workshop", "2025-12-26T15:00:00", 2, users.get(1).getId());
        createWorksession("Code Review Session", "2026-01-01T14:00:00", 2, users.get(1).getId());
        createWorksession("Peer Review", "2026-01-08T13:00:00", 2, users.get(1).getId());

        // Peer sessions (Lucas Van Der Berg - p2)
        createWorksession("Web Dev Project", "2025-12-23T14:00:00", 3, users.get(2).getId());
        createWorksession("Algorithms Practice", "2025-12-25T10:00:00", 2, users.get(2).getId());
        createWorksession("Weekend Coding", "2025-12-28T11:00:00", 3, users.get(2).getId());
        createWorksession("Sunday Brunch & Code", "2026-01-05T11:00:00", 3, users.get(2).getId());

        // Peer sessions (Sophie Martinez - p3)
        createWorksession("UI Design", "2025-12-24T16:00:00", 2, users.get(3).getId());
        createWorksession("Game Dev Session", "2025-12-29T15:00:00", 3, users.get(3).getId());
        createWorksession("Weekend Study", "2026-01-11T10:00:00", 3, users.get(3).getId());

        // Peer sessions (Liam Wilson - p4)
        createWorksession("Project Scoping", "2026-01-06T10:00:00", 2, users.get(4).getId());

        // Peer sessions (Olivia Brown - p5)
        createWorksession("Figma Prototyping", "2026-01-07T14:00:00", 2, users.get(5).getId());

        // Peer sessions (Noah Davis - p6)
        createWorksession("Vulnerability Research", "2026-01-08T09:00:00", 2, users.get(6).getId());

        // Group sessions (CS Study Group)
        if (!groups.isEmpty()) {
            createWorksession("ML Study Group", "2025-12-24T13:00:00", 2, groups.get(0).getId());
            createWorksession("Group Presentation Prep", "2025-12-26T14:00:00", 2, groups.get(0).getId());
            createWorksession("AI Workshop", "2025-12-31T10:00:00", 3, groups.get(0).getId());
            createWorksession("Exam Study Group", "2026-01-06T09:00:00", 3, groups.get(0).getId());
            createWorksession("Hackathon", "2026-01-04T10:00:00", 9, groups.get(0).getId());
        }

        // Group sessions (Mobile App Team 1)
        if (groups.size() > 3) {
            createWorksession("Mobile Team Sync", "2026-01-03T15:00:00", 1, groups.get(3).getId());
        }

        // Group sessions (Ethics in AI Discussion)
        if (groups.size() > 4) {
            createWorksession("AI Ethics Webinar", "2025-12-30T13:00:00", 2, groups.get(4).getId());
        }

        System.out.println(">>> Database initialized with 45+ worksessions for users and groups");
    }

    private void createWorksession(String title, String startTimeStr, float durationHours, Object collaboratorIdValue) {
        LocalDateTime startTime = LocalDateTime.parse(startTimeStr);
        
        // Extract UUID from either UserId or GroupId
        java.util.UUID uuid;
        if (collaboratorIdValue instanceof UserId userId) {
            uuid = userId.id();
        } else if (collaboratorIdValue instanceof GroupId groupId) {
            uuid = groupId.id();
        } else {
            return;
        }
        
        Worksession ws = new Worksession(
                title,
                new TimeRange(
                        startTime,
                        startTime.plusMinutes((long) (durationHours * 60))
                ),
                null,
                new CollaboratorId(uuid)
        );
        worksessionRepository.save(ws);
    }
}
