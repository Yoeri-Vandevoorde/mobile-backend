package be.ucll.craftsmanship.team11.PeerPlan.collaboration.commands;

import be.ucll.craftsmanship.team11.PeerPlan.collaboration.domain.valueObjects.GroupId;
import jakarta.validation.constraints.NotNull;

public record UpdateGroupCommand(
        @NotNull GroupId groupId,
        @NotNull String newName
) {}
