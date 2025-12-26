package com.example.authentication.iam.domain.model.entities;

import com.example.authentication.iam.domain.exceptions.RoleNotFoundException;
import com.example.authentication.iam.domain.model.valueobjects.Roles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@With
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(length = 20, unique = true)
  private Roles roles;

  public Role(Roles roles) {
    this.roles = roles;
  }

  public String getStringRole() {
    return this.roles.name();
  }

  public static Role getDefaultRole() {
    return new Role(Roles.ROLE_USER);
  }

  public static Role toRoleFromName(String roleName) {
    try {
      return new Role(Roles.valueOf(roleName));
    } catch (IllegalArgumentException e) {
      throw new RoleNotFoundException(roleName);
    }
  }

  public static List<Role> validateRoleSet(List<Role> roles) {
    if (roles == null || roles.isEmpty()) {
      return List.of(getDefaultRole());
    }
    return roles;
  }
}
