package be.ucll.craftsmanship.team11.PeerPlan.collaboration.commands;

import java.util.UUID;

import be.ucll.craftsmanship.team11.PeerPlan.collaboration.domain.valueObjects.GroupId;

public record RemoveMemberCommand(GroupId groupId, UUID userId){
}