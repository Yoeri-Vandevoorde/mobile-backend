package be.ucll.craftsmanship.team11.PeerPlan.identity.application.dto;

import be.ucll.craftsmanship.team11.PeerPlan.identity.domain.valueObjects.UserId;

public record AuthenticationResponse(
        String message,
        String token,
        String username,
        UserId userId
) {}
