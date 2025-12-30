package be.ucll.craftsmanship.team11.PeerPlan.planning.infrastructure;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import be.ucll.craftsmanship.team11.PeerPlan.planning.domain.Worksession;
import be.ucll.craftsmanship.team11.PeerPlan.planning.domain.valueObjects.CollaboratorId;
import be.ucll.craftsmanship.team11.PeerPlan.planning.domain.valueObjects.WorksessionId;

@Repository
public interface WorksessionRepository extends JpaRepository<Worksession, WorksessionId> {

    List<Worksession> findByCollaboratorId(CollaboratorId collaboratorId);

    boolean existsByTimeRange_StartTimeLessThanAndTimeRange_EndTimeGreaterThan(LocalDateTime endTime, LocalDateTime startTime);
}