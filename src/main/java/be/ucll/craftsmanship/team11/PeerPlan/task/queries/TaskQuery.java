package be.ucll.craftsmanship.team11.PeerPlan.task.queries;

import be.ucll.craftsmanship.team11.PeerPlan.identity.domain.valueObjects.UserId;

public record TaskQuery(
        UserId assignee
) {}