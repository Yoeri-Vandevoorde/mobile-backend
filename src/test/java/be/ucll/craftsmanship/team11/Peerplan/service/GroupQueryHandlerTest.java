package be.ucll.craftsmanship.team11.Peerplan.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import be.ucll.craftsmanship.team11.PeerPlan.collaboration.application.GroupQueryHandler;
import be.ucll.craftsmanship.team11.PeerPlan.collaboration.domain.Group;
import be.ucll.craftsmanship.team11.PeerPlan.collaboration.domain.valueObjects.GroupId;
import be.ucll.craftsmanship.team11.PeerPlan.collaboration.infrastructure.GroupRepository;

@ExtendWith(MockitoExtension.class)
class GroupQueryHandlerTest {

    @Mock
    private GroupRepository groupRepository;

    @InjectMocks
    private GroupQueryHandler handler;

    private UUID userId;

    @BeforeEach
    void setup() {
        userId = UUID.randomUUID();
    }

    @Test
    void shouldReturnAllGroups() {
        Group group1 = new Group("Admins", userId);
        group1.setId(new GroupId(UUID.randomUUID()));

        Group group2 = new Group("Users", userId);
        group2.setId(new GroupId(UUID.randomUUID()));

        when(groupRepository.findAll()).thenReturn(List.of(group1, group2));

        List<Group> result = handler.findAll();

        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyInAnyOrder(group1, group2);
        verify(groupRepository).findAll();
    }

    @Test
    void shouldReturnEmptyListWhenNoGroupsExist() {
        when(groupRepository.findAll()).thenReturn(List.of());

        List<Group> result = handler.findAll();

        assertThat(result).isEmpty();
        verify(groupRepository).findAll();
    }
}
