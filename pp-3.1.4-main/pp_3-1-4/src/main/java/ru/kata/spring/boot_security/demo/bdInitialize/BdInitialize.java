package ru.kata.spring.boot_security.demo.bdInitialize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

@Component
public class BdInitialize {
    private UserService userService = new UserServiceImpl();

    private RoleService roleService = new RoleServiceImpl();

    @Autowired
    public void setUserService(UserServiceImpl userServiceImpl) {
        this.userService = userServiceImpl;
    }

    @Autowired
    public void setRoleService(RoleServiceImpl roleServiceImpl) {
        this.roleService = roleServiceImpl;
    }

    public BdInitialize(UserServiceImpl userServiceImpl, RoleServiceImpl roleServiceImpl) {
        this.userService = userServiceImpl;
        this.roleService = roleServiceImpl;

        Role roleUser = new Role("ROLE_USER");
        Role roleAdmin = new Role("ROLE_ADMIN");

        User user = new User("user", "userovich", 21, "user@mail.ru", "user");
        User admin = new User("admin", "adminovich", 22, "admin@mail.ru", "admin");
        User adminAndUser = new User("adminUser", "adminUserovich", 23, "adminUser@mail.ru", "adminUser");

        user.addRole(roleUser);
        admin.addRole(roleAdmin);
        adminAndUser.addRole(roleUser);
        adminAndUser.addRole(roleAdmin);

        roleServiceImpl.saveRole(roleUser);
        roleServiceImpl.saveRole(roleAdmin);

        userServiceImpl.saveUser(user);
        userServiceImpl.saveUser(admin);
        userServiceImpl.saveUser(adminAndUser);

        if (admin.getRoles().contains(roleAdmin) && user.getRoles().contains(roleUser)) {
            System.out.println(1);
        } else if (admin.getRoles().contains(roleAdmin)) {
            System.out.println(2);
        } else if (admin.getRoles().contains(roleUser)) {
            System.out.println(3);
        }

    }
}
