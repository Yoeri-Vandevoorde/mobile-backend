package be.ucll.craftsmanship.team11.PeerPlan.task.commands;

import be.ucll.craftsmanship.team11.PeerPlan.task.domain.model.valueObjects.TaskId;

public record AddSubtaskCommand(
        TaskId taskId,
        String title
) {}
