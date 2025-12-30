package be.ucll.craftsmanship.team11.PeerPlan.identity.domain;

import be.ucll.craftsmanship.team11.PeerPlan.identity.domain.valueObjects.UserEmail;
import be.ucll.craftsmanship.team11.PeerPlan.identity.domain.valueObjects.UserId;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

    @EmbeddedId
    private UserId id;

    private String username;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "email"))
    private UserEmail email;

    private String password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Profile profile;

    public User(String username, UserEmail email, String password) {
        this.id = new UserId();
        this.username = username;
        this.email = email;
        this.password = password;
    }
    protected User() {}
}
