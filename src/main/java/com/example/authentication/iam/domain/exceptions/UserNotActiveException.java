package com.example.authentication.iam.domain.exceptions;

public class UserNotActiveException extends RuntimeException {
  public UserNotActiveException(String username) {
    super("User is not active: " + username);
  }
}
