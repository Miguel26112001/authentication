package com.example.authentication.iam.interfaces.rest.transform;

import com.example.authentication.iam.domain.model.commands.SignInCommand;
import com.example.authentication.iam.interfaces.rest.resources.SignInResource;

public class SignInCommandFromResourceAssembler {
  public static SignInCommand toCommandFromResource(SignInResource signInResource) {

    return new SignInCommand(
        signInResource.username(),
        signInResource.password());
  }
}
