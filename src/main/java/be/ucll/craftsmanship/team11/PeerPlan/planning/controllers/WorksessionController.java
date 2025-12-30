package be.ucll.craftsmanship.team11.PeerPlan.planning.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import be.ucll.craftsmanship.team11.PeerPlan.planning.application.WorksessionCommandHandler;
import be.ucll.craftsmanship.team11.PeerPlan.planning.application.WorksessionQueryHandler;
import be.ucll.craftsmanship.team11.PeerPlan.planning.commands.CreateWorksessionCommand;
import be.ucll.craftsmanship.team11.PeerPlan.planning.commands.UpdateWorksessionCommand;
import be.ucll.craftsmanship.team11.PeerPlan.planning.commands.DeleteWorksessionCommand;
import be.ucll.craftsmanship.team11.PeerPlan.planning.domain.Worksession;
import be.ucll.craftsmanship.team11.PeerPlan.planning.domain.valueObjects.WorksessionId;
import be.ucll.craftsmanship.team11.PeerPlan.planning.queries.FindAllWorksessionsQuery;
import be.ucll.craftsmanship.team11.PeerPlan.planning.queries.GetWorksessionByIdQuery;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/worksessions")
@RequiredArgsConstructor
public class WorksessionController {

    private final WorksessionCommandHandler commandHandler;
    private final WorksessionQueryHandler queryHandler;

    @GetMapping
    public List<Worksession> getAll() {
        return queryHandler.handle(new FindAllWorksessionsQuery());
    }

    @GetMapping("/{id}")
    public Worksession getById(@PathVariable String id) {
        return queryHandler.handle(new GetWorksessionByIdQuery(WorksessionId.from(id)));
    }

    @PostMapping
    public Worksession create(@RequestBody CreateWorksessionCommand command) {
        return commandHandler.handle(command);
    }

    @PutMapping("/{id}")
    public Worksession update(@PathVariable WorksessionId id, @RequestBody UpdateWorksessionCommand command) {
        return commandHandler.handle(id, command);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        commandHandler.handle(new DeleteWorksessionCommand(WorksessionId.from(id)));
    }
}