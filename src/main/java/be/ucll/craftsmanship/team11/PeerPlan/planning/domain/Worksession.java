package be.ucll.craftsmanship.team11.PeerPlan.planning.domain;

import be.ucll.craftsmanship.team11.PeerPlan.planning.domain.valueObjects.CollaboratorId;
import be.ucll.craftsmanship.team11.PeerPlan.planning.domain.valueObjects.TimeRange;
import be.ucll.craftsmanship.team11.PeerPlan.planning.domain.valueObjects.WorksessionId;
import be.ucll.craftsmanship.team11.PeerPlan.task.domain.model.valueObjects.TaskId;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@Entity
@Table(name = "worksessions")
public class Worksession {
    @EmbeddedId
    private WorksessionId id;

    @NotNull
    private String title;

    @Embedded
    private TimeRange timeRange;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "collaborator_id"))
    private CollaboratorId collaboratorId;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "subject_id"))
    private TaskId subjectId;

    public Worksession(String title, TimeRange timeRange, TaskId subjectId, CollaboratorId collaboratorId) {
        this.id = new WorksessionId();
        this.title = title;
        this.timeRange = timeRange;
        this.subjectId = subjectId;
        this.collaboratorId = collaboratorId;
    }

    protected Worksession() {
    }
}
