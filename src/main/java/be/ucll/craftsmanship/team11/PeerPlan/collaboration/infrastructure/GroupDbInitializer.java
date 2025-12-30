package be.ucll.craftsmanship.team11.PeerPlan.collaboration.infrastructure;

import be.ucll.craftsmanship.team11.PeerPlan.collaboration.domain.Group;
import be.ucll.craftsmanship.team11.PeerPlan.identity.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

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
        if (users.size() < 2) return;

        // Take first two users from database
        var owner = users.get(0).getId();
        var member = users.get(1).getId();

        Group devGroup = new Group("Development Team", owner);
        devGroup.addMember(member);

        groupRepository.save(devGroup);

        System.out.println(">>> Database initialized with one group: Development Team");
    }
}
