package com.example.authentication.iam.interfaces.rest.transform;

import com.example.authentication.iam.domain.model.commands.SignUpCommand;
import com.example.authentication.iam.domain.model.entities.Role;
import com.example.authentication.iam.interfaces.rest.resources.SignUpResource;

public class SignUpCommandFromResourceAssembler {
  public static SignUpCommand toCommandFromResource(SignUpResource resource) {
    var roles = resource.roles() != null
        ? resource.roles().stream().map(Role::toRoleFromName).toList()
        : null;

    var validatedRoles = Role.validateRoleSet(roles);

    return new SignUpCommand(
        resource.username(),
        resource.password(),
        validatedRoles);
  }
}
