package be.ucll.craftsmanship.team11.PeerPlan.planning.domain.valueObjects;

import be.ucll.craftsmanship.team11.PeerPlan.task.domain.model.valueObjects.SubtaskId;
import org.springframework.util.Assert;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Embeddable;
import java.util.Map;
import java.util.UUID;

@Embeddable
public record CollaboratorId(UUID value) {
    @JsonCreator
    public static CollaboratorId from(String value) {
        return new CollaboratorId(UUID.fromString(value));
    }

    @JsonValue
    public String toJson() {
        return value.toString();
    }

    public CollaboratorId {
        Assert.notNull(value, "Collaborator id cannot be null");
    }

    public CollaboratorId() {
        this(UUID.randomUUID());
    }
}
