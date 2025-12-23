package com.example.authentication.iam.application.internal.commandservices;

import com.example.authentication.iam.application.internal.outboundservices.hashing.HashingService;
import com.example.authentication.iam.application.internal.outboundservices.tokens.TokenService;
import com.example.authentication.iam.domain.model.aggregates.User;
import com.example.authentication.iam.domain.model.commands.SignInCommand;
import com.example.authentication.iam.domain.model.commands.SignUpCommand;
import com.example.authentication.iam.domain.services.UserCommandService;
import com.example.authentication.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import com.example.authentication.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.Optional;

public class UserCommandServiceImpl implements UserCommandService {

  private final UserRepository userRepository;
  private final HashingService hashingService;
  private final TokenService tokenService;
  private final RoleRepository roleRepository;

  public UserCommandServiceImpl(
      UserRepository userRepository,
      HashingService hashingService,
      TokenService tokenService,
      RoleRepository roleRepository) {
    this.userRepository = userRepository;
    this.hashingService = hashingService;
    this.tokenService = tokenService;
    this.roleRepository = roleRepository;
  }

  @Override
  public Optional<ImmutablePair<User, String>> handle(SignInCommand command) {
    var user = userRepository.findByUsername(command.username());
    if (user.isEmpty()) {
      throw new RuntimeException("User not found");
    }

    if (!hashingService.matches(command.password(), user.get().getHashedPassword())) {
      throw new RuntimeException("Invalid password");
    }

    var token = tokenService.generateToken(user.get().getUsername());
    return Optional.of(ImmutablePair.of(user.get(), token));
  }

  @Override
  public Optional<User> handle(SignUpCommand command) {
    if (userRepository.existsByUsername(command.username())) {
      throw new RuntimeException("Username already exists");
    }

    var roles = command.roles().stream().map(role ->
      roleRepository.findByRoles(role.getRoles())
          .orElseThrow(() -> new RuntimeException("Role not found")))
        .toList();

    var user = new User(command.username(), hashingService.encode(command.password()), roles);
    userRepository.save(user);

    return userRepository.findByUsername(command.username());
  }
}
