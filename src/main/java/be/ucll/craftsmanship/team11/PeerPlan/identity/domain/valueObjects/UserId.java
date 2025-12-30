package be.ucll.craftsmanship.team11.PeerPlan.identity.domain.valueObjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.util.Assert;

import java.util.UUID;

public record UserId(UUID id) {

    @JsonCreator
    public static UserId from(String value) {
        return new UserId(UUID.fromString(value));
    }

    @JsonValue
    public String toJson() {
        return id.toString();
    }

    public UserId {
        Assert.notNull(id, "value must not be null");
    }

    public UserId() {
        this(UUID.randomUUID());
    }

    public UserId getId() {
        return this;
    }
}