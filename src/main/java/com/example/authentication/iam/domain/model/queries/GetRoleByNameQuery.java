package com.example.authentication.iam.domain.model.queries;

import com.example.authentication.iam.domain.model.valueobjects.Roles;

public record GetRoleByNameQuery(
    Roles roles) {
}
