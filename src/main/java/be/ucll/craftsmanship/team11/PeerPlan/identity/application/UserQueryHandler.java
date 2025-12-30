package be.ucll.craftsmanship.team11.PeerPlan.identity.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import be.ucll.craftsmanship.team11.PeerPlan.identity.domain.User;
import be.ucll.craftsmanship.team11.PeerPlan.identity.infrastructure.UserRepository;
import be.ucll.craftsmanship.team11.PeerPlan.identity.queries.GetAllUsersQuery;
import be.ucll.craftsmanship.team11.PeerPlan.identity.queries.GetUserByEmailQuery;
import be.ucll.craftsmanship.team11.PeerPlan.identity.queries.GetUserByIdQuery;
import be.ucll.craftsmanship.team11.PeerPlan.shared.exception.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserQueryHandler {

    private final UserRepository userRepository;

    public User handle(GetUserByIdQuery query) {
        return userRepository.findById(query.id())
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    public List<User> handle(GetAllUsersQuery query) {
        return userRepository.findAll();
    }

    public User handle(GetUserByEmailQuery query) {
        return userRepository.findByEmail(query.email())
                .orElseThrow(() -> new NotFoundException("User not found"));
    }
}
