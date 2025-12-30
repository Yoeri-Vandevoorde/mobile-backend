package be.ucll.craftsmanship.team11.PeerPlan.identity.infrastructure;

import be.ucll.craftsmanship.team11.PeerPlan.identity.domain.Profile;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import be.ucll.craftsmanship.team11.PeerPlan.identity.domain.User;
import be.ucll.craftsmanship.team11.PeerPlan.identity.domain.valueObjects.UserEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;

@RequiredArgsConstructor
@Component("userDbInitializer")
@Order(1)
public class UserDbInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        userRepository.deleteAll();

        User alice = new User(
                "Alice",
                new UserEmail("alice@example.com"),
                passwordEncoder.encode("password123")
        );
        Profile aliceProfile = new Profile();
        aliceProfile.setUser(alice);
        alice.setProfile(aliceProfile);

        User bob = new User(
                "Bob",
                new UserEmail("bob@example.com"),
                passwordEncoder.encode("password123")
        );
        Profile bobProfile = new Profile();
        bobProfile.setUser(bob);
        bob.setProfile(bobProfile);

        userRepository.save(alice);
        userRepository.save(bob);

        System.out.println(">>> Database initialized with users Alice and Bob");
    }
}
