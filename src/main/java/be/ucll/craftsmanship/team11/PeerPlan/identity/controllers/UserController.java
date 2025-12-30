package be.ucll.craftsmanship.team11.PeerPlan.identity.controllers;

import be.ucll.craftsmanship.team11.PeerPlan.identity.application.UserCommandHandler;
import be.ucll.craftsmanship.team11.PeerPlan.identity.application.UserQueryHandler;
import be.ucll.craftsmanship.team11.PeerPlan.identity.application.dto.AuthenticationResponse;
import be.ucll.craftsmanship.team11.PeerPlan.identity.commands.DeleteUserCommand;
import be.ucll.craftsmanship.team11.PeerPlan.identity.commands.LoginUserCommand;
import be.ucll.craftsmanship.team11.PeerPlan.identity.commands.SignupUserCommand;
import be.ucll.craftsmanship.team11.PeerPlan.identity.commands.UpdateUsernameCommand;
import be.ucll.craftsmanship.team11.PeerPlan.identity.domain.User;
import be.ucll.craftsmanship.team11.PeerPlan.identity.domain.valueObjects.UserEmail;
import be.ucll.craftsmanship.team11.PeerPlan.identity.domain.valueObjects.UserId;
import be.ucll.craftsmanship.team11.PeerPlan.identity.queries.GetAllUsersQuery;
import be.ucll.craftsmanship.team11.PeerPlan.identity.queries.GetUserByEmailQuery;
import be.ucll.craftsmanship.team11.PeerPlan.identity.queries.GetUserByIdQuery;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.server.Cookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserCommandHandler commandHandler;
    private final UserQueryHandler queryHandler;

    @GetMapping
    public List<User> allUsers() {
        return queryHandler.handle(new GetAllUsersQuery());
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable String id) {
        return queryHandler.handle(new GetUserByIdQuery(UserId.from(id)));
    }

    @GetMapping("/email")
    public User getUserByEmail(@RequestParam String email) {
        return queryHandler.handle(new GetUserByEmailQuery(new UserEmail(email)));
    }

    @PostMapping("/login")
    public ResponseEntity<Object> authenticate(
            @RequestBody LoginUserCommand command,
            HttpServletResponse response
    ) {
        AuthenticationResponse auth = commandHandler.handle(command);

        ResponseCookie cookie = ResponseCookie.from("authToken", auth.token())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(3600)
                .sameSite(Cookie.SameSite.NONE.toString())
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        // Return the full auth response including the token for mobile app compatibility
        // The token is sent both as a cookie (for web) and in response body (for mobile)
        return ResponseEntity.ok(auth);
    }

    @PostMapping("/signup")
    public User signup(@RequestBody @Valid SignupUserCommand command) {
        return commandHandler.handle(command);
    }

    @PutMapping("/{id}")
    public User update(@PathVariable UserId id, @RequestBody @Valid UpdateUsernameCommand command) {
        var updatedIdCommand = new UpdateUsernameCommand(
                id,
                command.username()
        );
        return commandHandler.handle(updatedIdCommand);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        commandHandler.handle(new DeleteUserCommand(UserId.from(id)));
    }
}
