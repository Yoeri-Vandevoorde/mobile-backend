package be.ucll.craftsmanship.team11.PeerPlan.identity.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import be.ucll.craftsmanship.team11.PeerPlan.identity.domain.Profile;
import be.ucll.craftsmanship.team11.PeerPlan.identity.domain.valueObjects.UserId;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, UserId> {

}