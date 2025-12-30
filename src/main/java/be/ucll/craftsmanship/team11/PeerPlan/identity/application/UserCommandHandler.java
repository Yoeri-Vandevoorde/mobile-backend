package be.ucll.craftsmanship.team11.PeerPlan.identity.application;

import be.ucll.craftsmanship.team11.PeerPlan.identity.application.dto.AuthenticationResponse;
import be.ucll.craftsmanship.team11.PeerPlan.identity.commands.DeleteUserCommand;
import be.ucll.craftsmanship.team11.PeerPlan.identity.commands.LoginUserCommand;
import be.ucll.craftsmanship.team11.PeerPlan.identity.commands.SignupUserCommand;
import be.ucll.craftsmanship.team11.PeerPlan.identity.commands.UpdateUsernameCommand;
import be.ucll.craftsmanship.team11.PeerPlan.identity.domain.User;
import be.ucll.craftsmanship.team11.PeerPlan.identity.infrastructure.UserRepository;
import be.ucll.craftsmanship.team11.PeerPlan.identity.infrastructure.security.UserPrincipal;
import be.ucll.craftsmanship.team11.PeerPlan.shared.exception.NotFoundException;
import be.ucll.craftsmanship.team11.PeerPlan.shared.exception.ServiceException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class UserCommandHandler {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Transactional
    public User handle(SignupUserCommand command) {
        if (userRepository.existsByEmail(command.email())) {
            throw new ServiceException("Email " + command.email() + " is already taken");
        }
        if (userRepository.existsByUsername(command.username())) {
            throw new ServiceException("Username " + command.username() + " is already taken");
        }

        final var passwordHash = passwordEncoder.encode(command.password());
        final var user = new User(
                command.username(),
                command.email(),
                passwordHash
        );

        return userRepository.save(user);
    }

    @Transactional
    public AuthenticationResponse handle(LoginUserCommand command) {
        System.out.println(1);
        final var authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                command.email().value(),
                                command.password()
                        )
                );
        System.out.println(2);
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        User user = principal.getUser();

        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(
                "Authentication successful.",
                token,
                user.getUsername()
        );
    }

    @Transactional
    public User handle(UpdateUsernameCommand command) {
        User user = userRepository.findById(command.id())
                .orElseThrow(() -> new NotFoundException("User not found"));

        user.setUsername(command.username());

        return user;
    }

    @Transactional
    public void handle(DeleteUserCommand command) {
        userRepository.deleteById(command.id());
    }
}
