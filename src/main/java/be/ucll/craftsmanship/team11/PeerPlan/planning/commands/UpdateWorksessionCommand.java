package be.ucll.craftsmanship.team11.PeerPlan.planning.commands;

import be.ucll.craftsmanship.team11.PeerPlan.planning.domain.valueObjects.TimeRange;
import be.ucll.craftsmanship.team11.PeerPlan.task.domain.model.valueObjects.TaskId;

public record UpdateWorksessionCommand(
        String title,
        TimeRange timeRange,
        TaskId subjectId
) {
}
