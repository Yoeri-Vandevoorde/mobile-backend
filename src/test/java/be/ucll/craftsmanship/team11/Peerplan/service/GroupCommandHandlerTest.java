package be.ucll.craftsmanship.team11.Peerplan.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import be.ucll.craftsmanship.team11.PeerPlan.collaboration.application.GroupCommandHandler;
import be.ucll.craftsmanship.team11.PeerPlan.collaboration.commands.AddMemberCommand;
import be.ucll.craftsmanship.team11.PeerPlan.collaboration.commands.CreateGroupCommand;
import be.ucll.craftsmanship.team11.PeerPlan.collaboration.commands.DeleteGroupCommand;
import be.ucll.craftsmanship.team11.PeerPlan.collaboration.commands.RemoveMemberCommand;
import be.ucll.craftsmanship.team11.PeerPlan.collaboration.commands.UpdateGroupCommand;
import be.ucll.craftsmanship.team11.PeerPlan.collaboration.domain.Group;
import be.ucll.craftsmanship.team11.PeerPlan.collaboration.domain.valueObjects.GroupId;
import be.ucll.craftsmanship.team11.PeerPlan.collaboration.infrastructure.GroupRepository;

@ExtendWith(MockitoExtension.class)
class GroupCommandHandlerTest {

    @Mock
    private GroupRepository groupRepository;

    @InjectMocks
    private GroupCommandHandler handler;

    private UUID userId;
    private UUID anotherUserId;

    @BeforeEach
    void setup() {
        userId = UUID.randomUUID();
        anotherUserId = UUID.randomUUID();
    }

    @Test
    void shouldCreateGroup() {
        CreateGroupCommand command = new CreateGroupCommand( userId, "Admins");

        Group savedGroup = new Group("Admins", userId);
        when(groupRepository.save(any(Group.class))).thenReturn(savedGroup);

        Group result = handler.handle(command);

        assertThat(result.getName()).isEqualTo("Admins");
        assertThat(result.getMembers())
                .hasSize(1)
                .anyMatch(m -> m.getUserId().equals(userId));
        verify(groupRepository).save(any(Group.class));
    }

    @Test
    void shouldUpdateGroupNameSuccessfully() {
        Group group = new Group("Old Name", userId);
        group.setId(new GroupId(UUID.randomUUID()));

        UpdateGroupCommand command = new UpdateGroupCommand(group.getId(), "New Name");

        when(groupRepository.findById(group.getId())).thenReturn(Optional.of(group));
        when(groupRepository.save(any(Group.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Group result = handler.handle(command);

        assertThat(result.getName()).isEqualTo("New Name");
        verify(groupRepository).save(group);
    }

    @Test
    void updatingNonExistingGroupShouldFail() {
        GroupId randomId = new GroupId(UUID.randomUUID());
        UpdateGroupCommand command = new UpdateGroupCommand(randomId, "New Name");

        when(groupRepository.findById(randomId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> handler.handle(command))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Group not found");

        verify(groupRepository, never()).save(any());
    }

    @Test
    void shouldAddMember() {
        Group group = new Group("Admins", userId);
        group.setId(null);
        when(groupRepository.findById(any())).thenReturn(Optional.of(group));
        when(groupRepository.save(any())).thenReturn(group);

        AddMemberCommand command = new AddMemberCommand(group.getId(), anotherUserId);
        boolean result = handler.handle(command);

        assertThat(result).isTrue();
        assertThat(group.getMembers()).anyMatch(m -> m.getUserId().equals(anotherUserId));
        verify(groupRepository).save(group);
    }

    @Test
    void shouldRemoveMember() {
        Group group = new Group("Admins", userId);
        group.addMember(anotherUserId);
        when(groupRepository.findById(any())).thenReturn(Optional.of(group));
        when(groupRepository.save(any())).thenReturn(group);

        RemoveMemberCommand command = new RemoveMemberCommand(group.getId(), anotherUserId);
        handler.handle(command);

        assertThat(group.getMembers()).noneMatch(m -> m.getUserId().equals(anotherUserId));
        verify(groupRepository).save(group);
    }

    @Test
    void shouldDeleteGroup() {
        Group group = new Group("Admins", userId);
        when(groupRepository.findById(any())).thenReturn(Optional.of(group));

        DeleteGroupCommand command = new DeleteGroupCommand(group.getId(), userId);
        handler.handle(command);

        verify(groupRepository).delete(group);
    }

    @Test
    void deletingNonExistingGroupShouldFail() {
        when(groupRepository.findById(any())).thenReturn(Optional.empty());

        DeleteGroupCommand command = new DeleteGroupCommand(new GroupId(UUID.randomUUID()), userId);

        assertThatThrownBy(() -> handler.handle(command))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Group not found");

        verify(groupRepository, never()).delete(any());
    }
}
