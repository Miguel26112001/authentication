package com.example.authentication.iam.domain.model.commands;

public record SignInCommand(
    String username,
    String password) {
}
