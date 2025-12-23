package com.example.authentication.iam.application.internal.queryservices;

import com.example.authentication.iam.domain.model.entities.Role;
import com.example.authentication.iam.domain.model.queries.GetAllRolesQuery;
import com.example.authentication.iam.domain.model.queries.GetRoleByNameQuery;
import com.example.authentication.iam.domain.model.valueobjects.Roles;
import com.example.authentication.iam.domain.services.RoleQueryService;
import com.example.authentication.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleQueryServiceImpl implements RoleQueryService {
  private final RoleRepository roleRepository;

  public RoleQueryServiceImpl(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  @Override
  public List<Role> handle(GetAllRolesQuery query) {
    return roleRepository.findAll();
  }

  @Override
  public Optional<Role> handle(GetRoleByNameQuery query) {
    try {
      var roleName = query.roles().toUpperCase();
      if (!roleName.startsWith("ROLE_")) {
        roleName = "ROLE_" + roleName;
      }
      var roles = Roles.valueOf(roleName);
      return roleRepository.findByRoles(roles);
    } catch (IllegalArgumentException e) {
      return Optional.empty();
    }
  }
}
