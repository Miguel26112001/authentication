package com.example.authentication.iam.domain.services;

import com.example.authentication.iam.domain.model.aggregates.User;
import com.example.authentication.iam.domain.model.commands.SignInCommand;
import com.example.authentication.iam.domain.model.commands.SignUpCommand;
import com.example.authentication.iam.domain.model.commands.UpdateUserStatusCommand;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.Optional;

public interface UserCommandService {
  Optional<ImmutablePair<User, String>> handle(SignInCommand command);
  Optional<User> handle(SignUpCommand command);
  Optional<User> handle(UpdateUserStatusCommand command);
}
