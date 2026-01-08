package be.ucll.craftsmanship.team11.PeerPlan.collaboration.commands;

import java.util.UUID;

public record CreateGroupCommand (
        UUID userId,
        String groupName
) {}