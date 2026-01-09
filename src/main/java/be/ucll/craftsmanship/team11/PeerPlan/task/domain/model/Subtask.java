package be.ucll.craftsmanship.team11.PeerPlan.task.domain.model;

import org.springframework.util.Assert;

import be.ucll.craftsmanship.team11.PeerPlan.task.domain.model.valueObjects.Status;
import be.ucll.craftsmanship.team11.PeerPlan.task.domain.model.valueObjects.SubtaskId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

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
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Task task;

    public Subtask(String title) {
        Assert.notNull(title, "Title must not be null");
        this.id = new SubtaskId();
        this.title = title;
        this.status = Status.TODO;
    }

    public Subtask(String title, Status status) {
        Assert.notNull(title, "Title must not be null");
        this.id = new SubtaskId();
        this.title = title;
        this.status = status;
    }

    protected Subtask() {
    }
}
