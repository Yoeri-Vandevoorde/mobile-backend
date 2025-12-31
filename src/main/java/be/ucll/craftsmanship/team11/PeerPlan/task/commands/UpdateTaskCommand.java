package be.ucll.craftsmanship.team11.PeerPlan.task.commands;

import java.util.List;

import be.ucll.craftsmanship.team11.PeerPlan.task.domain.model.valueObjects.Status;
import be.ucll.craftsmanship.team11.PeerPlan.task.domain.model.valueObjects.SubtaskId;
import be.ucll.craftsmanship.team11.PeerPlan.task.domain.model.valueObjects.TaskId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class UpdateTaskCommand {

    @Value
    public static class UpdateSubtaskDTO {
        @NotNull
        SubtaskId id;
        @NotBlank
        String title;
        @NotNull
        Status status;
    }

    @NotNull
    TaskId id;

    @NotBlank
    String title;

    @NotNull
    Status status;

    @NotNull
    List<UpdateSubtaskDTO> subtasks;
}
