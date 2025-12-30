package be.ucll.craftsmanship.team11.PeerPlan.collaboration.application;

import be.ucll.craftsmanship.team11.PeerPlan.collaboration.commands.*;
import be.ucll.craftsmanship.team11.PeerPlan.collaboration.domain.Group;
import be.ucll.craftsmanship.team11.PeerPlan.collaboration.infrastructure.GroupRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class GroupCommandHandler {

    private final GroupRepository groupRepository;

    public GroupCommandHandler(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Transactional
    public Group handle(CreateGroupCommand command) {
    Group group = new Group(command.groupName(), command.userId());
    return groupRepository.save(group);
    }

    @Transactional
    public Group handle(UpdateGroupCommand command) {
        Group group = groupRepository.findById(command.groupId())
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));

        group.setName(command.newName());

        return groupRepository.save(group);
    }

    @Transactional
    public boolean handle(AddMemberCommand command) {
        Group group = groupRepository.findById(command.groupId())
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));
        group.addMember(command.userId());
        groupRepository.save(group);

        return true;
    }

    @Transactional
    public void handle(DeleteGroupCommand command) {
        Group group = groupRepository.findById(command.groupId())
                .orElseThrow(() -> new IllegalArgumentException("Group not found"));

        group.delete(command.userId());

        groupRepository.delete(group);
}

    @Transactional
    public void handle(RemoveMemberCommand command) {
    Group group = groupRepository.findById(command.groupId())
            .orElseThrow(() -> new IllegalArgumentException("Group not found"));

    group.removeMember(command.userId());
    groupRepository.save(group);
    return;
}
}