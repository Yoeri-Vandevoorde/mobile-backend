package be.ucll.craftsmanship.team11.Peerplan.model;

import java.time.LocalDateTime;
import java.util.UUID;

import be.ucll.craftsmanship.team11.PeerPlan.planning.domain.valueObjects.TimeRange;
import org.junit.jupiter.api.Test;

import be.ucll.craftsmanship.team11.PeerPlan.planning.domain.Worksession;
import be.ucll.craftsmanship.team11.PeerPlan.planning.domain.valueObjects.CollaboratorId;
import be.ucll.craftsmanship.team11.PeerPlan.task.domain.model.valueObjects.TaskId;

import static org.assertj.core.api.Assertions.assertThat;

class WorksessionTest {

    @Test
    void shouldCreateWorksessionWithConstructor() {
        String title = "Design Meeting";
        LocalDateTime startTime = LocalDateTime.of(2025, 12, 15, 10, 0);
        LocalDateTime endTime = LocalDateTime.of(2025, 12, 15, 11, 0);
        TaskId subjectId = new TaskId();
        CollaboratorId collaboratorId = new CollaboratorId(UUID.randomUUID());


        Worksession ws = new Worksession(title, new TimeRange(startTime, endTime), subjectId, collaboratorId);

        assertThat(ws.getTitle()).isEqualTo(title);
        assertThat(ws.getTimeRange().startTime()).isEqualTo(startTime);
        assertThat(ws.getTimeRange().endTime()).isEqualTo(endTime);
        assertThat(ws.getSubjectId()).isEqualTo(subjectId);
        assertThat(ws.getId()).isNull(); // EmbeddedId not set yet
    }

    @Test
    void shouldHaveCorrectEqualsAndHashCode() {
        LocalDateTime start = LocalDateTime.of(2025, 12, 15, 10, 0);
        LocalDateTime end = LocalDateTime.of(2025, 12, 15, 11, 0);
        TaskId subjectId = new TaskId(UUID.randomUUID());
        CollaboratorId collaboratorId = new CollaboratorId(UUID.randomUUID());

        Worksession ws1 = new Worksession("Meeting", new TimeRange(start, end), subjectId, collaboratorId);
        Worksession ws2 = new Worksession("Meeting", new TimeRange(start, end), subjectId, collaboratorId);

        assertThat(ws1).isEqualTo(ws2);
        assertThat(ws1.hashCode()).isEqualTo(ws2.hashCode());
    }
}
