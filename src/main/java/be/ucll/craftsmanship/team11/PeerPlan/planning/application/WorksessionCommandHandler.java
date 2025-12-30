package be.ucll.craftsmanship.team11.PeerPlan.planning.application;

import be.ucll.craftsmanship.team11.PeerPlan.planning.domain.valueObjects.WorksessionId;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import be.ucll.craftsmanship.team11.PeerPlan.identity.infrastructure.UserRepository;
import be.ucll.craftsmanship.team11.PeerPlan.planning.commands.CreateWorksessionCommand;
import be.ucll.craftsmanship.team11.PeerPlan.planning.commands.UpdateWorksessionCommand;
import be.ucll.craftsmanship.team11.PeerPlan.planning.commands.DeleteWorksessionCommand;
import be.ucll.craftsmanship.team11.PeerPlan.planning.domain.Worksession;
import be.ucll.craftsmanship.team11.PeerPlan.planning.infrastructure.WorksessionRepository;
import be.ucll.craftsmanship.team11.PeerPlan.shared.exception.NotFoundException;
import be.ucll.craftsmanship.team11.PeerPlan.shared.exception.ServiceException;
import be.ucll.craftsmanship.team11.PeerPlan.task.domain.model.valueObjects.SubtaskId;
import be.ucll.craftsmanship.team11.PeerPlan.task.domain.model.valueObjects.TaskId;
import be.ucll.craftsmanship.team11.PeerPlan.task.infrastructure.TaskRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WorksessionCommandHandler {

    private final WorksessionRepository worksessionRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @Transactional
    public Worksession handle(CreateWorksessionCommand command) {
        if (worksessionRepository.existsByTimeRange_StartTimeLessThanAndTimeRange_EndTimeGreaterThan(
                command.timeRange().endTime(), command.timeRange().startTime()
        )) {
            throw new ServiceException("Overlapping worksession already exists");
        }

        var taskId = new TaskId(command.subjectId().value());
        var subtaskId = new SubtaskId(command.subjectId().value());

        if (!taskRepository.existsById(taskId)
                && !taskRepository.existsBySubtasksId(subtaskId)) {
            throw new NotFoundException("No workable exists with value " + taskId.value());
        }

        var worksession = new Worksession(
                command.title(),
                command.timeRange(),
                command.subjectId(),
                command.collaboratorId()
        );

        return worksessionRepository.save(worksession);
    }

    @Transactional
    public Worksession handle(WorksessionId id, UpdateWorksessionCommand command) {
        var worksession = worksessionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Worksession not found"));

        if (worksessionRepository.existsByTimeRange_StartTimeLessThanAndTimeRange_EndTimeGreaterThan(
                command.timeRange().endTime(), command.timeRange().startTime()
        )) {
            throw new ServiceException("Overlapping worksession already exists");
        }

        var taskId = new TaskId(command.subjectId().value());
        var subtaskId = new SubtaskId(command.subjectId().value());

        if (!taskRepository.existsById(taskId)
                && !taskRepository.existsBySubtasksId(subtaskId)) {
            throw new NotFoundException("No workable exists with value " + taskId.value());
        }

        var newWorksession = new Worksession(
                id,
                command.title(),
                command.timeRange(),
                worksession.getCollaboratorId(),
                command.subjectId()
        );

        return worksessionRepository.save(newWorksession);
    }

    @Transactional
    public void handle(DeleteWorksessionCommand command) {
        if (!worksessionRepository.existsById(command.id())) {
            throw new NotFoundException("Worksession not found");
        }
        worksessionRepository.deleteById(command.id());
    }
}
