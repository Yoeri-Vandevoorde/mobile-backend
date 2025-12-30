package be.ucll.craftsmanship.team11.PeerPlan.identity.commands;

import be.ucll.craftsmanship.team11.PeerPlan.identity.domain.valueObjects.UserEmail;

public record LoginUserCommand (
  UserEmail email,
  String password
) {}
