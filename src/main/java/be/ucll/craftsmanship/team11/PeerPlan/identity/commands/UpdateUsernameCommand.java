package be.ucll.craftsmanship.team11.PeerPlan.identity.commands;

import be.ucll.craftsmanship.team11.PeerPlan.identity.domain.valueObjects.UserId;

public record UpdateUsernameCommand(
    UserId id,
    String username
) {}
