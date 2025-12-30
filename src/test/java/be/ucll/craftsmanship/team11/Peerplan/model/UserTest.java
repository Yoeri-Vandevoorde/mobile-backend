package be.ucll.craftsmanship.team11.Peerplan.model;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import be.ucll.craftsmanship.team11.PeerPlan.identity.domain.User;
import be.ucll.craftsmanship.team11.PeerPlan.identity.domain.valueObjects.UserEmail;
import be.ucll.craftsmanship.team11.PeerPlan.identity.domain.valueObjects.UserId;

import static org.assertj.core.api.Assertions.assertThat;


class UserTest {

    @Test
    void shouldCreateUserWithConstructor() {
        String username = "testUser";
        UserEmail email = new UserEmail("test@example.com");
        String password = "hashedSecret";

        User user = new User(username, email, password);

        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getPassword()).isEqualTo(password);
        assertThat(user.getUsername()).isEqualTo(username);
        assertThat(user.getId()).isNotNull(); // ID is generated in constructor
    }

    @Test
    void shouldHaveCorrectEqualsAndHashCode() {
        UserId userId = new UserId(UUID.randomUUID());
        String username = "sameUser";
        UserEmail email = new UserEmail("same@example.com");
        String hash = "pwd";

        User u1 = new User(username, email, hash);
        u1.setId(userId); // If you want to manually set ID for equals test
        User u2 = new User(username, email, hash);
        u2.setId(userId);

        assertThat(u1).isEqualTo(u2);
        assertThat(u1.hashCode()).isEqualTo(u2.hashCode());
    }
}