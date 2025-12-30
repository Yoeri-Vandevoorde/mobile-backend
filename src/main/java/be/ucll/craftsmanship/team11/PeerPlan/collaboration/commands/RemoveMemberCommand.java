package be.ucll.craftsmanship.team11.PeerPlan.collaboration.commands;

import be.ucll.craftsmanship.team11.PeerPlan.collaboration.domain.valueObjects.GroupId;
import be.ucll.craftsmanship.team11.PeerPlan.identity.domain.valueObjects.UserId;

public record RemoveMemberCommand(GroupId groupId, UserId userId){
}