package be.ucll.craftsmanship.team11.Peerplan.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import be.ucll.craftsmanship.team11.PeerPlan.identity.application.JwtService;
import be.ucll.craftsmanship.team11.PeerPlan.identity.application.UserCommandHandler;
import be.ucll.craftsmanship.team11.PeerPlan.identity.commands.DeleteUserCommand;
import be.ucll.craftsmanship.team11.PeerPlan.identity.commands.SignupUserCommand;
import be.ucll.craftsmanship.team11.PeerPlan.identity.commands.UpdateUsernameCommand;
import be.ucll.craftsmanship.team11.PeerPlan.identity.domain.User;
import be.ucll.craftsmanship.team11.PeerPlan.identity.domain.valueObjects.UserEmail;
import be.ucll.craftsmanship.team11.PeerPlan.identity.domain.valueObjects.UserId;
import be.ucll.craftsmanship.team11.PeerPlan.identity.infrastructure.UserRepository;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import be.ucll.craftsmanship.team11.PeerPlan.shared.exception.NotFoundException;
import be.ucll.craftsmanship.team11.PeerPlan.shared.exception.ServiceException;


@ExtendWith(MockitoExtension.class)
class UserCommandHandlerTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    JwtService jwtService;

    @InjectMocks
    UserCommandHandler handler;

    @Test
    void shouldSignupUser() {
        String username = "JohnDoe";
        UserEmail email = new UserEmail("john.doe@example.com");
        String rawPassword = "secret";
        String hashedPassword = "hashed-secret";

        SignupUserCommand command = new SignupUserCommand(email, rawPassword, username);

        when(userRepository.existsByEmail(email)).thenReturn(false);
        when(userRepository.existsByUsername(username)).thenReturn(false);
        when(passwordEncoder.encode(rawPassword)).thenReturn(hashedPassword);

        User savedUser = new User(username, email, hashedPassword);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User result = handler.handle(command);

        assertThat(result.getUsername()).isEqualTo(username);
        assertThat(result.getEmail()).isEqualTo(email);
        assertThat(result.getPassword()).isEqualTo(hashedPassword);

        verify(passwordEncoder).encode(rawPassword);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void shouldFailWhenEmailAlreadyTaken() {
        String username = "JohnDoe";
        UserEmail email = new UserEmail("john.doe@example.com");
        String rawPassword = "secret";

        SignupUserCommand command = new SignupUserCommand(email, rawPassword, username);

        when(userRepository.existsByEmail(email)).thenReturn(true);

        assertThatThrownBy(() -> handler.handle(command))
                .isInstanceOf(ServiceException.class)
                .hasMessageContaining("Email");

        verify(userRepository, never()).save(any());
    }

    @Test
    void shouldFailWhenUsernameAlreadyTaken() {
        String username = "JohnDoe";
        UserEmail email = new UserEmail("john.doe@example.com");
        String rawPassword = "secret";

        SignupUserCommand command = new SignupUserCommand(email, rawPassword, username);

        when(userRepository.existsByEmail(email)).thenReturn(false);
        when(userRepository.existsByUsername(username)).thenReturn(true);

        assertThatThrownBy(() -> handler.handle(command))
                .isInstanceOf(ServiceException.class)
                .hasMessageContaining("Username");

        verify(userRepository, never()).save(any());
    }

    @Test
    void shouldUpdateUsername() {
        UserId userId = new UserId(UUID.randomUUID());
        String oldUsername = "old";
        String newUsername = "new";
        UserEmail email = new UserEmail("test@example.com");

        User user = new User(oldUsername, email, "hash");
        user.setId(userId);

        UpdateUsernameCommand command = new UpdateUsernameCommand(userId, newUsername);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User result = handler.handle(command);

        assertThat(result.getUsername()).isEqualTo(newUsername);
    }

    @Test
    void updatingNonExistingUserShouldFail() {
        UserId userId = new UserId(UUID.randomUUID());

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        UpdateUsernameCommand command = new UpdateUsernameCommand(userId,"new");

        assertThatThrownBy(() -> handler.handle(command))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void shouldDeleteUser() {
        UserId userId = new UserId(UUID.randomUUID());

        DeleteUserCommand command = new DeleteUserCommand(userId);

        handler.handle(command);

        verify(userRepository).deleteById(userId);
    }
}