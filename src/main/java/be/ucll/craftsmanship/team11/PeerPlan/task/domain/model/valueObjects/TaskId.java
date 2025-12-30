package be.ucll.craftsmanship.team11.PeerPlan.task.domain.model.valueObjects;

import org.springframework.util.Assert;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Embeddable;
import java.util.UUID;

@Embeddable
public record TaskId(UUID value) {
    @JsonCreator
    public static TaskId from(String value) {
        return new TaskId(UUID.fromString(value));
    }

    @JsonValue
    public String toJson() {
        return value.toString();
    }

    public TaskId {
        Assert.notNull(value, "value must not be null");
    }

    public TaskId() {
        this(UUID.randomUUID());
    }
}
