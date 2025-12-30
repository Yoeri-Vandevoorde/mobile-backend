package be.ucll.craftsmanship.team11.PeerPlan.collaboration.domain;

import be.ucll.craftsmanship.team11.PeerPlan.collaboration.domain.entities.Member;
import be.ucll.craftsmanship.team11.PeerPlan.collaboration.domain.valueObjects.GroupId;
import be.ucll.craftsmanship.team11.PeerPlan.collaboration.domain.valueObjects.MemberRole;
import be.ucll.craftsmanship.team11.PeerPlan.identity.domain.valueObjects.UserId;
import be.ucll.craftsmanship.team11.PeerPlan.planning.domain.entities.Collaborator;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "groups")
@Data
@NoArgsConstructor
public class Group implements Collaborator<GroupId> {

    @EmbeddedId
    private GroupId id;

    @NotNull
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "group_id")
    private Set<Member> members = new HashSet<>();

    public Group(String name, UserId creatorId) {
        this.id = new GroupId();
        this.name = name;
        this.members.add(new Member(creatorId, MemberRole.OWNER));
    }

    public void addMember(UserId userId) {
        boolean alreadyMember = members.stream()
                .anyMatch(m -> m.getUserId().equals(userId));

        if (alreadyMember) {
            throw new IllegalStateException("User is already a member");
        }

        members.add(new Member(userId, MemberRole.MEMBER));
    }

    public void removeMember(UserId userId) {
        members.removeIf(m -> m.getUserId().equals(userId));
    }

    public boolean isOwner(UserId userId) {
        return members.stream()
                .anyMatch(m -> m.getUserId().equals(userId) && m.isOwner());
    }

    public void delete(UserId requesterId) {
        if (!isOwner(requesterId)) {
            throw new IllegalStateException("Only the group owner can delete the group");
        }
    }
}
