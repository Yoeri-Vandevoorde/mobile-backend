package be.ucll.craftsmanship.team11.PeerPlan.task.commands;

import be.ucll.craftsmanship.team11.PeerPlan.task.domain.model.valueObjects.TaskId;
import be.ucll.craftsmanship.team11.PeerPlan.task.domain.model.valueObjects.SubtaskId;

public record RemoveSubtaskCommand(
        TaskId taskId,
        SubtaskId subtaskId
) {}