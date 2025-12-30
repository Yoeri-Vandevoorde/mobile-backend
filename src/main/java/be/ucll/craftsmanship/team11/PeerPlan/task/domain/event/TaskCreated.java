package be.ucll.craftsmanship.team11.PeerPlan.task.domain.event;

import be.ucll.craftsmanship.team11.PeerPlan.task.domain.model.valueObjects.TaskId;

public record TaskCreated(TaskId id) {
}
