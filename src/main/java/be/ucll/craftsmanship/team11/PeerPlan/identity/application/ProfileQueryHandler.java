package be.ucll.craftsmanship.team11.PeerPlan.identity.application;

import org.springframework.stereotype.Service;

import be.ucll.craftsmanship.team11.PeerPlan.identity.domain.Profile;
import be.ucll.craftsmanship.team11.PeerPlan.identity.infrastructure.ProfileRepository;
import be.ucll.craftsmanship.team11.PeerPlan.identity.queries.GetProfileByIdQuery;
import be.ucll.craftsmanship.team11.PeerPlan.shared.exception.NotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileQueryHandler {
    private final ProfileRepository profileRepository;

    public Profile handle(GetProfileByIdQuery query) {
        return profileRepository.findById(query.id())
                .orElseThrow(() -> new NotFoundException("User not found"));
    }
}
