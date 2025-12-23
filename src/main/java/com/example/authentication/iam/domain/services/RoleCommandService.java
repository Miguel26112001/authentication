package com.example.authentication.iam.domain.services;

import com.example.authentication.iam.domain.model.commands.SeedRolesCommand;

public interface RoleCommandService {
  void handle(SeedRolesCommand command);
}
