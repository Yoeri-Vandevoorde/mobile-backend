package be.ucll.craftsmanship.team11.PeerPlan.identity.application;

import be.ucll.craftsmanship.team11.PeerPlan.identity.domain.User;
import be.ucll.craftsmanship.team11.PeerPlan.identity.domain.valueObjects.UserEmail;

public interface JwtService {

    String generateToken(UserEmail email);

    String generateToken(User user);
}
