package be.ucll.craftsmanship.team11.PeerPlan.task.domain.model;

import be.ucll.craftsmanship.team11.PeerPlan.task.domain.model.valueObjects.Status;

public interface Workable {
    String getTitle();
    Status getStatus();
}

