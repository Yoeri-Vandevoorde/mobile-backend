package be.ucll.craftsmanship.team11.PeerPlan.collaboration.domain.entities;

import java.util.Objects;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import be.ucll.craftsmanship.team11.PeerPlan.collaboration.domain.valueObjects.MemberId;
import be.ucll.craftsmanship.team11.PeerPlan.collaboration.domain.valueObjects.MemberRole;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "members")
public class Member {

    @EmbeddedId
    private MemberId id; 

    @Column(name = "user_id", nullable = false)
    @JsonIgnore
    private UUID userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberRole role;

    public Member(UUID userId, MemberRole role) {
        this.id = new MemberId();
        this.userId = userId;
        this.role = role;
    }

    @JsonProperty("userId")
    public String getUserIdString() {
        return userId != null ? userId.toString() : null;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public MemberId getId() {
        return id;
    }

    public void setId(MemberId id) {
        this.id = id;
    }

    public MemberRole getRole() {
        return role;
    }

    public void setRole(MemberRole role) {
        this.role = role;
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
