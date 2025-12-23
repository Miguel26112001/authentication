package com.example.authentication.iam.application.internal.commandservices;

import com.example.authentication.iam.domain.model.commands.SeedRolesCommand;
import com.example.authentication.iam.domain.model.entities.Role;
import com.example.authentication.iam.domain.model.valueobjects.Roles;
import com.example.authentication.iam.domain.services.RoleCommandService;
import com.example.authentication.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class RoleCommandServiceImpl implements RoleCommandService {
  private final RoleRepository roleRepository;

  public RoleCommandServiceImpl(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  @Override
  public void handle(SeedRolesCommand command) {
    Arrays.stream(Roles.values())
        .forEach(role -> {
          if (!roleRepository.existsByRoles(role)) {
            roleRepository.save(new Role(Roles.valueOf(role.name())));
          }
        });
  }
}