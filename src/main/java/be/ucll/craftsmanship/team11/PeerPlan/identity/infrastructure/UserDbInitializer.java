package be.ucll.craftsmanship.team11.PeerPlan.identity.infrastructure;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import be.ucll.craftsmanship.team11.PeerPlan.identity.domain.Profile;
import be.ucll.craftsmanship.team11.PeerPlan.identity.domain.User;
import be.ucll.craftsmanship.team11.PeerPlan.identity.domain.valueObjects.UserEmail;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component("userDbInitializer")
@Order(1)
public class UserDbInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        userRepository.deleteAll();

        // Main user
        User alice = new User(
                "Alice Chen",
                new UserEmail("alice@example.com"),
                passwordEncoder.encode("password123")
        );
        Profile aliceProfile = new Profile();
        aliceProfile.setUser(alice);
        alice.setProfile(aliceProfile);
        userRepository.save(alice);

        // Peers - friends
        User emma = new User(
                "Emma Johnson",
                new UserEmail("emma@example.com"),
                passwordEncoder.encode("password123")
        );
        Profile emmaProfile = new Profile();
        emmaProfile.setUser(emma);
        emma.setProfile(emmaProfile);
        userRepository.save(emma);

        User lucas = new User(
                "Lucas Van Der Berg",
                new UserEmail("lucas@example.com"),
                passwordEncoder.encode("password123")
        );
        Profile lucasProfile = new Profile();
        lucasProfile.setUser(lucas);
        lucas.setProfile(lucasProfile);
        userRepository.save(lucas);

        User sophie = new User(
                "Sophie Martinez",
                new UserEmail("sophie@example.com"),
                passwordEncoder.encode("password123")
        );
        Profile sophieProfile = new Profile();
        sophieProfile.setUser(sophie);
        sophie.setProfile(sophieProfile);
        userRepository.save(sophie);

        User noah = new User(
                "Noah Davis",
                new UserEmail("noah@example.com"),
                passwordEncoder.encode("password123")
        );
        Profile noahProfile = new Profile();
        noahProfile.setUser(noah);
        noah.setProfile(noahProfile);
        userRepository.save(noah);

        // Other persons
        User liam = new User(
                "Liam Wilson",
                new UserEmail("liam@example.com"),
                passwordEncoder.encode("password123")
        );
        Profile liamProfile = new Profile();
        liamProfile.setUser(liam);
        liam.setProfile(liamProfile);
        userRepository.save(liam);

        User olivia = new User(
                "Olivia Brown",
                new UserEmail("olivia@example.com"),
                passwordEncoder.encode("password123")
        );
        Profile oliviaProfile = new Profile();
        oliviaProfile.setUser(olivia);
        olivia.setProfile(oliviaProfile);
        userRepository.save(olivia);

        User harper = new User(
                "Harper Lee",
                new UserEmail("harper@example.com"),
                passwordEncoder.encode("password123")
        );
        Profile harperProfile = new Profile();
        harperProfile.setUser(harper);
        harper.setProfile(harperProfile);
        userRepository.save(harper);

        System.out.println(">>> Database initialized with 8 users (main user + 7 peers)");
    }
}
