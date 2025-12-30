package be.ucll.craftsmanship.team11.PeerPlan.collaboration.commands;

import be.ucll.craftsmanship.team11.PeerPlan.identity.domain.valueObjects.UserId;

public record CreateGroupCommand (
        UserId userId,
        String groupName
) {}