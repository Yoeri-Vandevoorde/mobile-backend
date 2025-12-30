package be.ucll.craftsmanship.team11.PeerPlan.task.domain.model;

import be.ucll.craftsmanship.team11.PeerPlan.identity.domain.valueObjects.UserId;
import be.ucll.craftsmanship.team11.PeerPlan.task.domain.model.valueObjects.SubtaskId;
import be.ucll.craftsmanship.team11.PeerPlan.task.domain.model.valueObjects.TaskId;
import be.ucll.craftsmanship.team11.PeerPlan.task.domain.model.valueObjects.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Data
@Table(name = "tasks")
public class Task implements Workable {

    @EmbeddedId
    private TaskId id;

    @NotBlank
    private String title;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "assignee_id", nullable = false))
    private UserId assignee;

    @OneToMany(
            mappedBy = "task", // field in Subtask pointing back to Task
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private final List<Subtask> subtasks = new ArrayList<>();

    public Task(String title, UserId assignee) {
        Assert.notNull(title, "Title must not be null");
        Assert.notNull(assignee, "Assignee must not be null");

        this.id = new TaskId();
        this.title = title;
        this.assignee = assignee;
        this.status = Status.TODO;
    }
    protected Task() {
    }

    public void addSubtask(Subtask subtask) {
        this.subtasks.add(subtask);
        subtask.setTask(this);
    }

    public void removeSubtask(Subtask subtask) {
        subtasks.remove(subtask);
    }

    public Optional<Subtask> findSubtaskById(SubtaskId subtaskId) {
        return subtasks.stream()
                .filter(subtask -> subtask.getId().equals(subtaskId))
                .findFirst();
    }

    @Override
    public Status getStatus() {
        if (subtasks.isEmpty()) return status;

        boolean allCompleted = subtasks.stream().allMatch(c -> c.getStatus() == Status.DONE);
        if (allCompleted) return Status.DONE;

        boolean anyInProgress = subtasks.stream().anyMatch(c -> c.getStatus() == Status.DOING);
        if (anyInProgress) return Status.DOING;

        return Status.TODO;
    }
}