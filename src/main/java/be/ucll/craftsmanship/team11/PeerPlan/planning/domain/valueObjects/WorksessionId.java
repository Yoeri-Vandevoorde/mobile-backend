package be.ucll.craftsmanship.team11.PeerPlan.planning.domain.valueObjects;

import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import jakarta.persistence.Embeddable;
import java.util.UUID;

@Embeddable
public record WorksessionId(UUID id) {
    public WorksessionId {
        Assert.notNull(id, "value must not be null");
    }

    public WorksessionId() {
        this(UUID.randomUUID());
    }

    @JsonCreator
    public static WorksessionId from(String value) {
        return new WorksessionId(UUID.fromString(value));
    }

    @JsonValue
    public String toJson() {
        return id.toString();
    }
}
