package be.ucll.craftsmanship.team11.Peerplan.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import be.ucll.craftsmanship.team11.PeerPlan.collaboration.domain.Group;
import be.ucll.craftsmanship.team11.PeerPlan.identity.domain.valueObjects.UserId;

class GroupTest {
    @Test
    void shouldAddMember() {
        UserId creatorId = new UserId(UUID.randomUUID());
        Group group = new Group("Admins", creatorId.id());

        UserId newMemberId = new UserId(UUID.randomUUID());
        group.addMember(newMemberId.id());

        assertThat(group.getMembers())
            .hasSize(2) // creator + new member
            .anyMatch(member -> member.getUserId().equals(newMemberId.id()));
    }

    @Test
    void shouldRemoveMember() {
        UserId creatorId = new UserId(UUID.randomUUID());
        Group group = new Group("Admins", creatorId.id());

        UserId newMemberId = new UserId(UUID.randomUUID());
        group.addMember(newMemberId.id());

        group.removeMember(newMemberId.id());

        assertThat(group.getMembers())
            .hasSize(1)
            .allMatch(member -> member.getUserId().equals(creatorId.id()));
    }

    @Test
    void removingNonExistingMemberShouldDoNothing() {
        UserId creatorId = new UserId(UUID.randomUUID());
        Group group = new Group("Admins", creatorId.id());

        group.removeMember(new UserId(UUID.randomUUID()).id()); // random ID not in group

        assertThat(group.getMembers())
            .hasSize(1)
            .allMatch(member -> member.getUserId().equals(creatorId.id()));
    }

    @Test
    void shouldNotAllowDuplicateMembers() {
        UserId creatorId = new UserId(UUID.randomUUID());
        Group group = new Group("Admins", creatorId.id());

        UserId memberId = new UserId(UUID.randomUUID());
        group.addMember(memberId.id());

        // adding the same member again should throw
        try {
            group.addMember(memberId.id());
        } catch (IllegalStateException ignored) {
        }

        assertThat(group.getMembers())
            .hasSize(2) // creator + member
            .anyMatch(member -> member.getUserId().equals(memberId.id()));
    }

    @Test
    void ownerShouldBeCorrect() {
        UserId creatorId = new UserId(UUID.randomUUID());
        Group group = new Group("Admins", creatorId.id());

        assertThat(group.isOwner(creatorId.id())).isTrue();

        UserId memberId = new UserId(UUID.randomUUID());
        group.addMember(memberId.id());

        assertThat(group.isOwner(memberId.id())).isFalse();
    }
}
