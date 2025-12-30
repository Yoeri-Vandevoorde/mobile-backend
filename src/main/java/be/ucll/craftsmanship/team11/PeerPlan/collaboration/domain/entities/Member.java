package be.ucll.craftsmanship.team11.PeerPlan.collaboration.domain.entities;

import java.util.Objects;
import be.ucll.craftsmanship.team11.PeerPlan.collaboration.domain.valueObjects.MemberId;
import be.ucll.craftsmanship.team11.PeerPlan.collaboration.domain.valueObjects.MemberRole;
import be.ucll.craftsmanship.team11.PeerPlan.identity.domain.valueObjects.UserId;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "members")
public class Member {

    @EmbeddedId
    private MemberId id; 

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "user_id", nullable = false))
    private UserId userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberRole role;

    public Member(UserId userId, MemberRole role) {
        this.id = new MemberId();
        this.userId = userId;
        this.role = role;
    }

    public UserId getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Member)) return false;
        Member member = (Member) o;
        return Objects.equals(userId, member.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    public boolean isOwner() {
        return role == MemberRole.OWNER;
    }
}
