package com.example.authentication.iam.domain.services;

import com.example.authentication.iam.domain.model.entities.Role;
import com.example.authentication.iam.domain.model.queries.GetAllRolesQuery;
import com.example.authentication.iam.domain.model.queries.GetRoleByNameQuery;

import java.util.List;
import java.util.Optional;

public interface RoleQueryService {
  List<Role> handle(GetAllRolesQuery query);
  Optional<Role> handle(GetRoleByNameQuery query);
}
