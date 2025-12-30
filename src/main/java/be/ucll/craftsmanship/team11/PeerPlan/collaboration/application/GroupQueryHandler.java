package be.ucll.craftsmanship.team11.PeerPlan.collaboration.application;

import be.ucll.craftsmanship.team11.PeerPlan.collaboration.domain.Group;
import be.ucll.craftsmanship.team11.PeerPlan.collaboration.infrastructure.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupQueryHandler {

    private final GroupRepository groupRepository;

    public GroupQueryHandler(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public List<Group> findAll() {
        return groupRepository.findAll();
    }
}