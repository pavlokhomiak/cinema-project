package com.dev.cinema.dao;

import com.dev.cinema.model.Role;
import java.util.Optional;

public interface RoleDao {
    Role add(Role role);

    Optional<Role> getRoleByName(Role.RoleName roleName);
}
