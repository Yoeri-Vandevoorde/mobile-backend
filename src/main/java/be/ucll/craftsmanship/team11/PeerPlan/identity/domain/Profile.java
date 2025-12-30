package be.ucll.craftsmanship.team11.PeerPlan.identity.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import be.ucll.craftsmanship.team11.PeerPlan.identity.domain.valueObjects.UserId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profile {

    @EmbeddedId
    private UserId id;

    private String bio;

    private String studyProgram;

    private int yearOfStudy;

    @OneToOne
    @MapsId
    @JoinColumn(name = "value")
    @JsonIgnore
    private User user;

    public Profile(String bio, String studyProgram, int yearOfStudy, User user) {
        this.bio = bio;
        this.studyProgram = studyProgram;
        this.yearOfStudy = yearOfStudy;
        this.user = user;
    }
}
