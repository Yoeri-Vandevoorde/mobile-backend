package be.ucll.craftsmanship.team11.PeerPlan.identity.domain.valueObjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import jakarta.persistence.Column;

public record UserEmail(
        @Column(nullable = false, unique = true)
        String value
) {
    @JsonCreator
    public static UserEmail from(String value) {
        return new UserEmail(value);
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    public UserEmail {
        if (value == null || !value.matches("[^@]+@[^@]+\\.[^@]+")) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }
}
