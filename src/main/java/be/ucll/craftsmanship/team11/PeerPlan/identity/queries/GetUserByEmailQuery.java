package be.ucll.craftsmanship.team11.PeerPlan.identity.queries;

import be.ucll.craftsmanship.team11.PeerPlan.identity.domain.valueObjects.UserEmail;

public record GetUserByEmailQuery(UserEmail email) {}
