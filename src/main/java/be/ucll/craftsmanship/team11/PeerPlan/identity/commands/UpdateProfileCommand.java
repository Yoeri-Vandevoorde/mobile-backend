package be.ucll.craftsmanship.team11.PeerPlan.identity.commands;

import be.ucll.craftsmanship.team11.PeerPlan.identity.domain.Profile;
import be.ucll.craftsmanship.team11.PeerPlan.identity.domain.valueObjects.UserId;

public record UpdateProfileCommand(UserId userId, Profile profile) {
}