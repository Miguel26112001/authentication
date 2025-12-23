package com.example.authentication.iam.interfaces.rest.transform;

import com.example.authentication.iam.domain.model.aggregates.User;
import com.example.authentication.iam.domain.model.entities.Role;
import com.example.authentication.iam.interfaces.rest.resources.UserResource;

public class UserResourceFromEntityAssembler {
  public static UserResource toResourceFromEntity(User user) {

    var roles = user.getRoles().stream()
        .map(Role::getStringRole)
        .toList();

    return new UserResource(user.getId(), user.getUsername(), roles);
  }
}
