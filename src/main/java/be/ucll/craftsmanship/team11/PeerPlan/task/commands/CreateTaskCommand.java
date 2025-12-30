package be.ucll.craftsmanship.team11.PeerPlan.task.commands;

import be.ucll.craftsmanship.team11.PeerPlan.identity.domain.valueObjects.UserId;

public record CreateTaskCommand(
        String title,
        UserId creatorId
) {}