package com.dev.cinema.util.inject;

import com.dev.cinema.model.Role;
import com.dev.cinema.model.User;
import com.dev.cinema.security.AuthenticationService;
import com.dev.cinema.service.RoleService;
import com.dev.cinema.service.UserService;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class InjectData {
    private UserService userService;
    private RoleService roleService;
    private AuthenticationService authenticationService;

    public InjectData(UserService userService,
                      RoleService roleService,
                      AuthenticationService authenticationService) {
        this.userService = userService;
        this.roleService = roleService;
        this.authenticationService = authenticationService;
    }

    @PostConstruct
    public void inject() {
        Role admin = new Role();
        admin.setRoleName(Role.RoleName.valueOf("ADMIN"));
        roleService.add(admin);
        Role user = new Role();
        user.setRoleName(Role.RoleName.valueOf("USER"));
        roleService.add(user);

        User userAdmin = new User();
        userAdmin.setEmail("admin@admin");
        userAdmin.setPassword("admin");
        userAdmin.setRoles(Set.of(roleService.getRoleByName(Role.RoleName.ADMIN)));
        userService.add(userAdmin);

        User userAdminUser = new User();
        userAdminUser.setEmail("adminuser@adminuser");
        userAdminUser.setPassword("adminuser");
        userAdminUser.setRoles(Set.of(
                roleService.getRoleByName(Role.RoleName.ADMIN),
                roleService.getRoleByName(Role.RoleName.USER)));
        userService.add(userAdminUser);
    }
}
