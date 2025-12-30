package be.ucll.craftsmanship.team11.PeerPlan.identity.application;

import be.ucll.craftsmanship.team11.PeerPlan.identity.commands.UpdateProfileCommand;
import be.ucll.craftsmanship.team11.PeerPlan.identity.domain.Profile;
import be.ucll.craftsmanship.team11.PeerPlan.identity.domain.User;
import be.ucll.craftsmanship.team11.PeerPlan.identity.infrastructure.UserRepository;

public class ProfileCommandHandler {

    private final UserRepository userRepository;

    public ProfileCommandHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void handle(UpdateProfileCommand command) {
        User user = userRepository.findById(command.userId().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Profile newProfile = command.profile();
        newProfile.setUser(user);
        user.setProfile(newProfile);

        userRepository.save(user);
    }
}
