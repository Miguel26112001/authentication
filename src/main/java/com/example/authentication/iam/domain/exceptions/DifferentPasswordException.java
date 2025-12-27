package com.example.authentication.iam.domain.exceptions;

public class DifferentPasswordException extends RuntimeException {
  public DifferentPasswordException() {
    super("The password of the user is incorrect.");
  }
}
