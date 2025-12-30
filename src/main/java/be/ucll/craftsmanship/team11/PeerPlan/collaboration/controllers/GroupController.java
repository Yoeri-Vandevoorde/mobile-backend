package be.ucll.craftsmanship.team11.PeerPlan.collaboration.controllers;

import be.ucll.craftsmanship.team11.PeerPlan.collaboration.application.GroupCommandHandler;
import be.ucll.craftsmanship.team11.PeerPlan.collaboration.application.GroupQueryHandler;
import be.ucll.craftsmanship.team11.PeerPlan.collaboration.commands.AddMemberCommand;
import be.ucll.craftsmanship.team11.PeerPlan.collaboration.commands.CreateGroupCommand;
import be.ucll.craftsmanship.team11.PeerPlan.collaboration.commands.DeleteGroupCommand;
import be.ucll.craftsmanship.team11.PeerPlan.collaboration.commands.RemoveMemberCommand;
import be.ucll.craftsmanship.team11.PeerPlan.collaboration.commands.UpdateGroupCommand;
import be.ucll.craftsmanship.team11.PeerPlan.collaboration.domain.Group;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

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
        return queryHandler.findAll();
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
