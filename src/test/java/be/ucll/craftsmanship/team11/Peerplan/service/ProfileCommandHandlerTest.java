package be.ucll.craftsmanship.team11.Peerplan.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import be.ucll.craftsmanship.team11.PeerPlan.identity.application.ProfileCommandHandler;
import be.ucll.craftsmanship.team11.PeerPlan.identity.commands.UpdateProfileCommand;
import be.ucll.craftsmanship.team11.PeerPlan.identity.domain.Profile;
import be.ucll.craftsmanship.team11.PeerPlan.identity.domain.User;
import be.ucll.craftsmanship.team11.PeerPlan.identity.domain.valueObjects.UserEmail;
import be.ucll.craftsmanship.team11.PeerPlan.identity.domain.valueObjects.UserId;
import be.ucll.craftsmanship.team11.PeerPlan.identity.infrastructure.UserRepository;

@ExtendWith(MockitoExtension.class)
class ProfileCommandHandlerTest {

    @Mock
    private UserRepository userRepository;

    private ProfileCommandHandler handler;

    private UserId userId;

    @BeforeEach
    void setUp() {
        handler = new ProfileCommandHandler(userRepository);
        userId = new UserId(UUID.randomUUID());
    }

    @Test
    void shouldUpdateProfileSuccessfully() {
        String username = "john_doe";
        UserEmail email = new UserEmail("john@example.com");
        String passwordHash = "hashed_password";

        User existingUser = new User(username, email, passwordHash);
        existingUser.setId(userId);

        Profile newProfile = new Profile("I love coding", "Computer Science", 2, existingUser);

        UpdateProfileCommand command = new UpdateProfileCommand(userId, newProfile);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        handler.handle(command);

        // Assert
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        Profile savedProfile = savedUser.getProfile();

        assertThat(savedProfile).isNotNull();
        assertThat(savedProfile.getBio()).isEqualTo("I love coding");
        assertThat(savedProfile.getStudyProgram()).isEqualTo("Computer Science");
        assertThat(savedProfile.getYearOfStudy()).isEqualTo(2);
        assertThat(savedProfile.getUser()).isEqualTo(savedUser);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        UserId missingUserId = new UserId(UUID.randomUUID());
        Profile profile = new Profile("Bio", "Math", 1, null);
        UpdateProfileCommand command = new UpdateProfileCommand(missingUserId, profile);

        when(userRepository.findById(missingUserId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> handler.handle(command));
    }
}
