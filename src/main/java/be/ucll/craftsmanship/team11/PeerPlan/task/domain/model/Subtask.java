package be.ucll.craftsmanship.team11.PeerPlan.task.domain.model;

import be.ucll.craftsmanship.team11.PeerPlan.task.domain.model.valueObjects.SubtaskId;
import be.ucll.craftsmanship.team11.PeerPlan.task.domain.model.valueObjects.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import org.springframework.util.Assert;

@Entity
@Data
@Table(name = "subtasks")
public class Subtask implements Workable {

    @EmbeddedId
    private SubtaskId id;

    @NotBlank
    private String title;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    public Subtask(String title) {
        Assert.notNull(title, "Title must not be null");
        this.id = new SubtaskId();
        this.title = title;
    }
    protected Subtask() {}
}

