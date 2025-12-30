package be.ucll.craftsmanship.team11.PeerPlan.task.application;

import be.ucll.craftsmanship.team11.PeerPlan.task.queries.TaskQuery;
import be.ucll.craftsmanship.team11.PeerPlan.task.domain.model.Task;
import be.ucll.craftsmanship.team11.PeerPlan.task.infrastructure.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskQueryHandler {

    private final TaskRepository taskRepository;

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public List<Task> filterTasks(TaskQuery query) {
        if (query.assignee() != null) {
            return taskRepository.findByAssignee(query.assignee());
        }
        return taskRepository.findAll();
    }
}
