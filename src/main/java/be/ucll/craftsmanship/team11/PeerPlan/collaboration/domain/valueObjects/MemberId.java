package be.ucll.craftsmanship.team11.PeerPlan.collaboration.domain.valueObjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.util.Assert;

import java.util.UUID;

public record MemberId(UUID id) {

    @JsonCreator
    public static MemberId from(String value) {
        return new MemberId(UUID.fromString(value));
    }

    @JsonValue
    public String toJson() {
        return id.toString();
    }

    public MemberId {
        Assert.notNull(id, "value must not be null");
    }

    public MemberId() {
        this(UUID.randomUUID());
    }
}
