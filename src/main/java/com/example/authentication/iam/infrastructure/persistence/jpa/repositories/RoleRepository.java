package com.example.authentication.iam.infrastructure.persistence.jpa.repositories;

import com.example.authentication.iam.domain.model.entities.Role;
import com.example.authentication.iam.domain.model.valueobjects.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByRoles(Roles roles);
  boolean existsByRoles(Roles roles);
}
