package be.ucll.craftsmanship.team11.Peerplan.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import be.ucll.craftsmanship.team11.PeerPlan.identity.domain.valueObjects.UserEmail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import be.ucll.craftsmanship.team11.PeerPlan.identity.application.UserQueryHandler;
import be.ucll.craftsmanship.team11.PeerPlan.identity.domain.User;
import be.ucll.craftsmanship.team11.PeerPlan.identity.domain.valueObjects.UserId;
import be.ucll.craftsmanship.team11.PeerPlan.identity.infrastructure.UserRepository;
import be.ucll.craftsmanship.team11.PeerPlan.identity.queries.GetAllUsersQuery;
import be.ucll.craftsmanship.team11.PeerPlan.identity.queries.GetUserByEmailQuery;
import be.ucll.craftsmanship.team11.PeerPlan.identity.queries.GetUserByIdQuery;
import be.ucll.craftsmanship.team11.PeerPlan.shared.exception.NotFoundException;

@ExtendWith(MockitoExtension.class)
class UserQueryHandlerTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserQueryHandler handler;

    private User user;

    @BeforeEach
    void setup() {
        user = new User("Test User", new UserEmail("test@example.com"), "password");
        user.setId(new UserId(UUID.randomUUID()));
    }

    @Test
    void shouldReturnUserById() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        User result = handler.handle(new GetUserByIdQuery(user.getId()));

        assertThat(result).isEqualTo(user);
        verify(userRepository).findById(user.getId());
    }

    @Test
    void shouldThrowWhenUserByIdNotFound() {
        UUID id = UUID.randomUUID();
        UserId userId = new UserId(id);
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> handler.handle(new GetUserByIdQuery(userId)))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("User not found");

        verify(userRepository).findById(userId);
    }

    @Test
    void shouldReturnAllUsers() {
        User anotherUser = new User("Other User", new UserEmail("other@example.com"), "password");
        anotherUser.setId(new UserId(UUID.randomUUID()));

        when(userRepository.findAll()).thenReturn(List.of(user, anotherUser));

        List<User> result = handler.handle(new GetAllUsersQuery());

        assertThat(result).containsExactlyInAnyOrder(user, anotherUser);
        verify(userRepository).findAll();
    }

    @Test
    void shouldReturnUserByEmail() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        User result = handler.handle(new GetUserByEmailQuery(user.getEmail()));

        assertThat(result).isEqualTo(user);
        verify(userRepository).findByEmail(user.getEmail());
    }

    @Test
    void shouldThrowWhenUserByEmailNotFound() {
        UserEmail email = new UserEmail("missing@example.com");
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> handler.handle(new GetUserByEmailQuery(email)))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("User not found");

        verify(userRepository).findByEmail(email);
    }
}
