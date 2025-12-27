package com.example.authentication.iam.domain.model.commands;

public record UpdateUserStatusCommand(Long userId, boolean isActive) {
}
