package be.ucll.craftsmanship.team11.PeerPlan.identity.application.dto;

public record AuthenticationResponse(
        String message,
        String token,
        String username
) {}
