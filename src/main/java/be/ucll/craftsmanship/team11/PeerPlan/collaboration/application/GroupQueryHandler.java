package be.ucll.craftsmanship.team11.PeerPlan.collaboration.application;

import java.util.List;

import org.springframework.stereotype.Service;

import be.ucll.craftsmanship.team11.PeerPlan.collaboration.domain.Group;
import be.ucll.craftsmanship.team11.PeerPlan.collaboration.domain.valueObjects.GroupId;
import be.ucll.craftsmanship.team11.PeerPlan.collaboration.infrastructure.GroupRepository;

@Service
public class GroupQueryHandler {

    private final GroupRepository groupRepository;

    public GroupQueryHandler(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public List<Group> findAll() {
        return groupRepository.findAll();
    }

    public Group findById(GroupId id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Group not found with id: " + id));
    }
}