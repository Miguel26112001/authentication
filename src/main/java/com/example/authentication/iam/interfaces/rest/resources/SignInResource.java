package com.example.authentication.iam.interfaces.rest.resources;

public record SignInResource(
    String username,
    String password) {
}
