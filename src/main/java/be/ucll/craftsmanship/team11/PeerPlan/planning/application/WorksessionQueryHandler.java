package be.ucll.craftsmanship.team11.PeerPlan.planning.application;

import java.util.List;

import org.springframework.stereotype.Component;

import be.ucll.craftsmanship.team11.PeerPlan.planning.domain.Worksession;
import be.ucll.craftsmanship.team11.PeerPlan.planning.infrastructure.WorksessionRepository;
import be.ucll.craftsmanship.team11.PeerPlan.planning.queries.FindAllWorksessionsQuery;
import be.ucll.craftsmanship.team11.PeerPlan.planning.queries.GetWorksessionByIdQuery;
import be.ucll.craftsmanship.team11.PeerPlan.shared.exception.NotFoundException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WorksessionQueryHandler {

    private final WorksessionRepository worksessionRepository;

    public List<Worksession> handle(FindAllWorksessionsQuery query) {
        return worksessionRepository.findAll();
    }

    public Worksession handle(GetWorksessionByIdQuery query) {
        return worksessionRepository.findById(query.id()).orElseThrow(() -> new NotFoundException("Worksession not found"));
    }
}
