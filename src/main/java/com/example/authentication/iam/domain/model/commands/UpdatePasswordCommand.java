package com.example.authentication.iam.domain.model.commands;

public record UpdatePasswordCommand(Long userId, String currentPassword, String newPassword) {
}
