package be.ucll.craftsmanship.team11.PeerPlan.collaboration.infrastructure;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import be.ucll.craftsmanship.team11.PeerPlan.collaboration.domain.Group;
import be.ucll.craftsmanship.team11.PeerPlan.identity.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;

@Component("groupDbInitializer")
@RequiredArgsConstructor
@Order(2)
public class GroupDbInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    @Override
    public void run(String... args) {
        groupRepository.deleteAll();

        var users = userRepository.findAll();
        if (users.isEmpty()) return;

        var owner = users.get(0).getId();
        var owner2 = users.get(3).getId();

        Group csStudyGroup = new Group("CS Study Group", owner.id());
        for (int i = 1; i < Math.min(users.size(), 8); i++) {
            csStudyGroup.addMember(users.get(i).getId().id());
        }
        groupRepository.save(csStudyGroup);

        Group dbProjectTeam = new Group("Database Project Team", owner.id());
        dbProjectTeam.addMember(users.get(1).getId().id());
        dbProjectTeam.addMember(users.get(2).getId().id());
        groupRepository.save(dbProjectTeam);

        Group algorithmCircle = new Group("Algorithms Study Circle", owner2.id());
        for (int i = 1; i < Math.min(users.size(), 2); i++) {
            algorithmCircle.addMember(users.get(i).getId().id());
        }
        groupRepository.save(algorithmCircle);

        Group mobileTeam = new Group("Mobile App Team 1", owner.id());
        for (int i = 1; i < Math.min(users.size(), 5); i++) {
            mobileTeam.addMember(users.get(i).getId().id());
        }
        groupRepository.save(mobileTeam);

        Group ethicsGroup = new Group("Ethics in AI Discussion", owner2.id());
        for (int i = 0; i < Math.min(users.size(), 1); i++) {
            ethicsGroup.addMember(users.get(i).getId().id());
        }
        groupRepository.save(ethicsGroup);

        System.out.println(">>> Database initialized with 5 groups");
    }
}
