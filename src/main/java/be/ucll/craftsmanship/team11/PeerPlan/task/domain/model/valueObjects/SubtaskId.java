package be.ucll.craftsmanship.team11.PeerPlan.task.domain.model.valueObjects;

import jakarta.persistence.Embeddable;
import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.UUID;

@Embeddable
public record SubtaskId(UUID value) {
    @JsonCreator
    public static SubtaskId from(String value) {
        return new SubtaskId(UUID.fromString(value));
    }

    @JsonValue
    public String toJson() {
        return value.toString();
    }

    public SubtaskId {
        Assert.notNull(value, "Subtask value cannot be null");
    }

    public SubtaskId() {
        this(UUID.randomUUID());
    }
}