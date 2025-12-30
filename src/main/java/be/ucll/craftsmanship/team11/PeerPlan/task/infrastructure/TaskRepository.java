package be.ucll.craftsmanship.team11.PeerPlan.task.infrastructure;

import be.ucll.craftsmanship.team11.PeerPlan.identity.domain.valueObjects.UserId;
import be.ucll.craftsmanship.team11.PeerPlan.task.domain.model.Task;
import be.ucll.craftsmanship.team11.PeerPlan.task.domain.model.valueObjects.SubtaskId;
import be.ucll.craftsmanship.team11.PeerPlan.task.domain.model.valueObjects.TaskId;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, TaskId> {

    List<Task> findByAssignee(UserId assignee);

    boolean existsBySubtasksId(SubtaskId id);
}
