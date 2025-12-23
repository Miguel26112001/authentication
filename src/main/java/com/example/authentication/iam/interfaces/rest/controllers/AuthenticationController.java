package com.example.authentication.iam.interfaces.rest.controllers;

import com.example.authentication.iam.domain.services.UserCommandService;
import com.example.authentication.iam.interfaces.rest.resources.AuthenticatedUserResource;
import com.example.authentication.iam.interfaces.rest.resources.SignInResource;
import com.example.authentication.iam.interfaces.rest.resources.SignUpResource;
import com.example.authentication.iam.interfaces.rest.resources.UserResource;
import com.example.authentication.iam.interfaces.rest.transform.AuthenticatedUserResourceFromEntityAssembler;
import com.example.authentication.iam.interfaces.rest.transform.SignInCommandFromResourceAssembler;
import com.example.authentication.iam.interfaces.rest.transform.SignUpCommandFromResourceAssembler;
import com.example.authentication.iam.interfaces.rest.transform.UserResourceFromEntityAssembler;
import com.example.authentication.shared.interfaces.rest.resources.MessageResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/authentication", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Authentication", description = "Authentication Endpoints")
public class AuthenticationController {
  private final UserCommandService userCommandService;

  public AuthenticationController(UserCommandService userCommandService) {
    this.userCommandService = userCommandService;
  }

  @PostMapping("/sign-in")
  @Operation(summary = "Sign in", description = "Sign in")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User authenticated"),
      @ApiResponse(responseCode = "400", description = "Bad request (User not found or Invalid password)")
  })
  public ResponseEntity<?> signIn(
      @RequestBody SignInResource signInResource) {

    var signInCommand = SignInCommandFromResourceAssembler
        .toCommandFromResource(signInResource);
    var authenticatedUser = userCommandService.handle(signInCommand);
    var authenticatedUserResource = AuthenticatedUserResourceFromEntityAssembler
        .toResourceFromEntity(
            authenticatedUser.get().getLeft(), authenticatedUser.get().getRight());
    return ResponseEntity.ok(authenticatedUserResource);
  }

  @PostMapping("/sign-up")
  @Operation(summary = "Sign up", description = "Sign up a new user")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "User created"),
      @ApiResponse(responseCode = "400", description = "Bad request")
  })
  public ResponseEntity<UserResource> signUp(@RequestBody SignUpResource signUpResource) {
    var signUpCommand = SignUpCommandFromResourceAssembler
        .toCommandFromResource(signUpResource);
    var user = userCommandService.handle(signUpCommand);
    var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
    return new ResponseEntity<>(userResource, HttpStatus.CREATED);
  }
}
