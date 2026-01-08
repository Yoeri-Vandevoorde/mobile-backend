package be.ucll.craftsmanship.team11.PeerPlan.collaboration.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import be.ucll.craftsmanship.team11.PeerPlan.collaboration.application.GroupCommandHandler;
import be.ucll.craftsmanship.team11.PeerPlan.collaboration.application.GroupQueryHandler;
import be.ucll.craftsmanship.team11.PeerPlan.collaboration.commands.AddMemberCommand;
import be.ucll.craftsmanship.team11.PeerPlan.collaboration.commands.CreateGroupCommand;
import be.ucll.craftsmanship.team11.PeerPlan.collaboration.commands.DeleteGroupCommand;
import be.ucll.craftsmanship.team11.PeerPlan.collaboration.commands.RemoveMemberCommand;
import be.ucll.craftsmanship.team11.PeerPlan.collaboration.commands.UpdateGroupCommand;
import be.ucll.craftsmanship.team11.PeerPlan.collaboration.domain.Group;
import be.ucll.craftsmanship.team11.PeerPlan.collaboration.domain.valueObjects.GroupId;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/groups")
public class GroupController {

    private final GroupCommandHandler commandHandler;
    private final GroupQueryHandler queryHandler;

    public GroupController(GroupCommandHandler commandHandler, GroupQueryHandler queryHandler) {
        this.commandHandler = commandHandler;
        this.queryHandler = queryHandler;
    }

    @GetMapping
    public List<Group> allGroups() {
        List<Group> groups = queryHandler.findAll();
        System.out.println(">>> Fetching all groups: " + groups.size() + " groups found");
        for (Group g : groups) {
            System.out.println("  - Group: " + g.getName() + " (id: " + g.getId() + ", members: " + g.getMembers().size() + ")");
        }
        return groups;
    }

    @GetMapping("/{id}")
    public Group getGroupById(@PathVariable String id) {
        Group group = queryHandler.findById(GroupId.from(id));
        System.out.println(">>> Fetching group: " + group.getName() + " (id: " + id + ", members: " + group.getMembers().size() + ")");
        return group;
    }

    @PostMapping("/create")
    public Group createGroup(@RequestBody @Valid CreateGroupCommand command) {
        return commandHandler.handle(command);
    }

    @PutMapping("/update")
    public Group updateGroup(@RequestBody @Valid UpdateGroupCommand command) {
        return commandHandler.handle(command);
    }

    @PutMapping("/addMember")
    public void addUserToGroup(@RequestBody @Valid AddMemberCommand command) {
        commandHandler.handle(command);
    }

    @PutMapping("/removeMember")
    public void removeUserFromGroup(@RequestBody @Valid RemoveMemberCommand command) {
        commandHandler.handle(command);
    }

    @DeleteMapping
    public void deleteGroup(@RequestBody @Valid DeleteGroupCommand command) {
        commandHandler.handle(command);
    }
}