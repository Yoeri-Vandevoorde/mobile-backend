package be.ucll.craftsmanship.team11.Peerplan.model;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import be.ucll.craftsmanship.team11.PeerPlan.collaboration.domain.Group;
import be.ucll.craftsmanship.team11.PeerPlan.identity.domain.valueObjects.UserId;
import static org.assertj.core.api.Assertions.assertThat;

class GroupTest {
    @Test
    void shouldAddMember() {
        UserId creatorId = new UserId(UUID.randomUUID());
        Group group = new Group("Admins", creatorId);

        UserId newMemberId = new UserId(UUID.randomUUID());
        group.addMember(newMemberId);

        assertThat(group.getMembers())
            .hasSize(2) // creator + new member
            .anyMatch(member -> member.getUserId().equals(newMemberId));
    }

    @Test
    void shouldRemoveMember() {
        UserId creatorId = new UserId(UUID.randomUUID());
        Group group = new Group("Admins", creatorId);

        UserId newMemberId = new UserId(UUID.randomUUID());
        group.addMember(newMemberId);

        group.removeMember(newMemberId);

        assertThat(group.getMembers())
            .hasSize(1)
            .allMatch(member -> member.getUserId().equals(creatorId));
    }

    @Test
    void removingNonExistingMemberShouldDoNothing() {
        UserId creatorId = new UserId(UUID.randomUUID());
        Group group = new Group("Admins", creatorId);

        group.removeMember(new UserId(UUID.randomUUID())); // random ID not in group

        assertThat(group.getMembers())
            .hasSize(1)
            .allMatch(member -> member.getUserId().equals(creatorId));
    }

    @Test
    void shouldNotAllowDuplicateMembers() {
        UserId creatorId = new UserId(UUID.randomUUID());
        Group group = new Group("Admins", creatorId);

        UserId memberId = new UserId(UUID.randomUUID());
        group.addMember(memberId);

        // adding the same member again should throw
        try {
            group.addMember(memberId);
        } catch (IllegalStateException ignored) {
        }

        assertThat(group.getMembers())
            .hasSize(2) // creator + member
            .anyMatch(member -> member.getUserId().equals(memberId));
    }

    @Test
    void ownerShouldBeCorrect() {
        UserId creatorId = new UserId(UUID.randomUUID());
        Group group = new Group("Admins", creatorId);

        assertThat(group.isOwner(creatorId)).isTrue();

        UserId memberId = new UserId(UUID.randomUUID());
        group.addMember(memberId);

        assertThat(group.isOwner(memberId)).isFalse();
    }
}
