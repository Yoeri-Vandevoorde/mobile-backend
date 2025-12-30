package be.ucll.craftsmanship.team11.Peerplan.model;

import org.junit.jupiter.api.Test;

import be.ucll.craftsmanship.team11.PeerPlan.identity.domain.valueObjects.UserId;
import be.ucll.craftsmanship.team11.PeerPlan.task.domain.model.Subtask;
import be.ucll.craftsmanship.team11.PeerPlan.task.domain.model.Task;
import be.ucll.craftsmanship.team11.PeerPlan.task.domain.model.valueObjects.Status;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TaskTest {
    @Test
    void shouldCreateTaskWithTitle() {
        String title = "Implement Feature X";
        UserId assigneeId = new UserId(java.util.UUID.randomUUID());

        Task task = new Task(title, assigneeId);

        assertThat(task.getTitle()).isEqualTo(title);
        assertThat(task.getStatus()).isEqualTo(Status.TODO);
        assertThat(task.getSubtasks()).isEmpty();
        assertThat(task.getId()).isNotNull();
    }

    @Test
    void shouldThrowExceptionWhenTitleIsNull() {
        UserId assigneeId = new UserId(java.util.UUID.randomUUID());
        assertThatThrownBy(() -> new Task(null, assigneeId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Title must not be null");
    }

    @Test
    void shouldAddSubtask() {
        UserId assigneeId = new UserId(java.util.UUID.randomUUID());
        Task task = new Task("Main Task", assigneeId);
        Subtask subtask = new Subtask("Subtask 1");

        task.addSubtask(subtask);

        assertThat(task.getSubtasks())
                .hasSize(1)
                .contains(subtask);
    }

    @Test
    void addingMultipleSubtasksShouldWork() {
        UserId assigneeId = new UserId(java.util.UUID.randomUUID());
        Task task = new Task("Main Task", assigneeId);
        Subtask s1 = new Subtask("Subtask 1");
        Subtask s2 = new Subtask("Subtask 2");

        task.addSubtask(s1);
        task.addSubtask(s2);

        assertThat(task.getSubtasks())
                .hasSize(2)
                .containsExactly(s1, s2);
    }
}