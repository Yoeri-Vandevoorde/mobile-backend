package be.ucll.craftsmanship.team11.PeerPlan.collaboration.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.hibernate.validator.constraints.Length;

import be.ucll.craftsmanship.team11.PeerPlan.collaboration.domain.entities.Member;
import be.ucll.craftsmanship.team11.PeerPlan.collaboration.domain.valueObjects.GroupId;
import be.ucll.craftsmanship.team11.PeerPlan.collaboration.domain.valueObjects.MemberRole;
import jakarta.persistence.CascadeType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "groups")
@NoArgsConstructor
public class Group {

    @EmbeddedId
    private GroupId id;

    @NotNull
    @Length(min = 3, max = 50, message = "Group name must be between 3 and 50 characters")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "group_id")
    private Set<Member> members = new HashSet<>();

    public Group(String name, UUID creatorId) {
        this.id = new GroupId();
        this.name = name;
        this.members.add(new Member(creatorId, MemberRole.OWNER));
    }

    public void addMember(UUID userId) {
        boolean alreadyMember = members.stream()
                .anyMatch(m -> m.getUserId().equals(userId));

        if (alreadyMember) {
            throw new IllegalStateException("User is already a member");
        }

        members.add(new Member(userId, MemberRole.MEMBER));
    }

    public void removeMember(UUID userId) {
        members.removeIf(m -> m.getUserId().equals(userId));
    }

    public boolean isOwner(UUID userId) {
        return members.stream()
                .anyMatch(m -> m.getUserId().equals(userId) && m.isOwner());
    }

    public void delete(UUID requesterId) {
        if (!isOwner(requesterId)) {
            throw new IllegalStateException("Only the group owner can delete the group");
        }
    }

    public void setId(GroupId id) {
        this.id = id;
    }

    public GroupId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Member> getMembers() {
        return members;
    }

    public void setName(String newName) {
        this.name = newName;
    }
}
