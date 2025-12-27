package com.example.authentication.iam.interfaces.rest.transform;

import com.example.authentication.iam.domain.model.commands.UpdatePasswordCommand;
import com.example.authentication.iam.interfaces.rest.resources.UpdatePasswordResource;

public class UpdatePasswordCommandFromResourceAssembler {
    public static UpdatePasswordCommand toCommandFromResource(Long userId, UpdatePasswordResource resource) {
        return new UpdatePasswordCommand(userId, resource.currentPassword(), resource.newPassword());
    }
}
