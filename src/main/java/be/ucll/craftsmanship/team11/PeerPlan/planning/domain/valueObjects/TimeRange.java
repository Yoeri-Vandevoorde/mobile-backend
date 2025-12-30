package be.ucll.craftsmanship.team11.PeerPlan.planning.domain.valueObjects;

import org.springframework.util.Assert;

import java.time.LocalDateTime;

public record TimeRange(
        LocalDateTime startTime,
        LocalDateTime endTime
) {
    public TimeRange {
        Assert.notNull(startTime, "Start time must not be null");
        Assert.notNull(endTime, "End time must not be null");
        Assert.isTrue(startTime.isBefore(endTime), "Start time must be before endTime time");
    }
}
