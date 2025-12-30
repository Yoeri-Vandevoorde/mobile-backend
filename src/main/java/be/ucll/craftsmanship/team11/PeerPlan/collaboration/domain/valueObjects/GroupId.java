package be.ucll.craftsmanship.team11.PeerPlan.collaboration.domain.valueObjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.util.Assert;

import java.util.UUID;

public record GroupId(UUID id) {

    @JsonCreator
    public static GroupId from(String value) {
        return new GroupId(UUID.fromString(value));
    }

    @JsonValue
    public String toJson() {
        return id.toString();
    }

    public GroupId {
        Assert.notNull(id, "value must not be null");
    }

    public GroupId() {
        this(UUID.randomUUID());
    }
}