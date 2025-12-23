package com.example.authentication.iam.interfaces.rest.transform;

import com.example.authentication.iam.domain.model.entities.Role;
import com.example.authentication.iam.interfaces.rest.resources.RoleResource;

public class RoleResourceFromEntityAssembler {
  public static RoleResource toResourceFromEntity(Role role) {

    return new RoleResource(
        role.getId(),
        role.getStringRole());
  }
}
