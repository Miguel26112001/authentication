package com.example.authentication.iam.interfaces.rest.controllers;

import com.example.authentication.iam.domain.model.commands.UpdateUserStatusCommand;
import com.example.authentication.iam.domain.model.queries.GetAllUsersQuery;
import com.example.authentication.iam.domain.model.queries.GetUserByIdQuery;
import com.example.authentication.iam.domain.services.UserCommandService;
import com.example.authentication.iam.domain.services.UserQueryService;
import com.example.authentication.iam.interfaces.rest.resources.UpdatePasswordResource;
import com.example.authentication.iam.interfaces.rest.resources.UserResource;
import com.example.authentication.iam.interfaces.rest.transform.UpdatePasswordCommandFromResourceAssembler;
import com.example.authentication.iam.interfaces.rest.transform.UserResourceFromEntityAssembler;
import com.example.authentication.shared.interfaces.rest.resources.MessageResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Users", description = "User Management Endpoints")
public class UsersController {
  private final UserQueryService userQueryService;
  private final UserCommandService userCommandService;

  public UsersController(UserQueryService userQueryService, UserCommandService userCommandService) {
    this.userQueryService = userQueryService;
    this.userCommandService = userCommandService;
  }

  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
  @Operation(summary = "Get all users", description = "Get all users registered in the system.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Users found",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResource.class))),
      @ApiResponse(responseCode = "401", description = "Unauthorized",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResource.class)))
  })
  public ResponseEntity<List<UserResource>> getAllUsers() {
    var getAllUsersQuery = new GetAllUsersQuery();
    var users = userQueryService.handle(getAllUsersQuery);
    var userResources = users.stream()
        .map(UserResourceFromEntityAssembler::toResourceFromEntity)
        .toList();
    return ResponseEntity.ok(userResources);
  }

  @GetMapping(value = "/{userId}")
  @PreAuthorize("hasRole('ADMIN') or (hasRole('USER'))")
  @Operation(summary = "Get user by ID", description = "Get a specific user filtered by its id.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User found",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResource.class))),
      @ApiResponse(responseCode = "401", description = "Unauthorized",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResource.class))),
      @ApiResponse(responseCode = "404", description = "User not found",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResource.class)))
  })
  public ResponseEntity<?> getUserById(@PathVariable Long userId) {
    var getUserByIdQuery = new GetUserByIdQuery(userId);
    var user = userQueryService.handle(getUserByIdQuery);
    if (user.isEmpty()) {
      return ResponseEntity.status(404).body(new MessageResource("User not found with ID: " + userId));
    }
    var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
    return ResponseEntity.ok(userResource);
  }

  @PatchMapping(value = "/{userId}/status")
  @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and #userId == principal.id)")
  @Operation(summary = "Update user status", description = "Update the status of a specific user.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User status updated",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResource.class))),
      @ApiResponse(responseCode = "401", description = "Unauthorized",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResource.class))),
      @ApiResponse(responseCode = "404", description = "User not found",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResource.class)))
  })
  public ResponseEntity<UserResource> updateUserStatus(@PathVariable Long userId, @RequestParam boolean isActive) {
    var updateUserStatusCommand = new UpdateUserStatusCommand(userId, isActive);
    var updatedUser = userCommandService.handle(updateUserStatusCommand);
    if (updatedUser.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(updatedUser.get());
    return ResponseEntity.ok(userResource);
  }

  @PatchMapping(value = "/{userId}/password")
  @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and #userId == principal.id)")
  @Operation(summary = "Update user password", description = "Update the password of a specific user.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User password updated",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResource.class))),
      @ApiResponse(responseCode = "400", description = "Weak password or invalid request",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResource.class))),
      @ApiResponse(responseCode = "401", description = "Unauthorized",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResource.class))),
      @ApiResponse(responseCode = "404", description = "User not found",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResource.class)))
  })
  public ResponseEntity<UserResource> updateUserPassword(@PathVariable Long userId, @RequestBody UpdatePasswordResource resource) {
    var updatePasswordCommand = UpdatePasswordCommandFromResourceAssembler.toCommandFromResource(userId, resource);
    var updatedUser = userCommandService.handle(updatePasswordCommand);
    if (updatedUser.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(updatedUser.get());
    return ResponseEntity.ok(userResource);
  }
}