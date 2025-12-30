package be.ucll.craftsmanship.team11.PeerPlan.task.domain.model.valueObjects;

import java.time.Instant;
public record Deadline(
        Instant deadline
) {
    public boolean hasPassed() {
        return Instant.now().isAfter(deadline);
    }
}
