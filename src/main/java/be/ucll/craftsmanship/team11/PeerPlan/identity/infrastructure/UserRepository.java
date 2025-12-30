package be.ucll.craftsmanship.team11.PeerPlan.identity.infrastructure;

import be.ucll.craftsmanship.team11.PeerPlan.identity.domain.User;
import be.ucll.craftsmanship.team11.PeerPlan.identity.domain.valueObjects.UserEmail;
import be.ucll.craftsmanship.team11.PeerPlan.identity.domain.valueObjects.UserId;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, UserId> {

    boolean existsByUsername(String username);

    boolean existsByEmail(UserEmail email);

    Optional<User> findByEmail(UserEmail email);
}
