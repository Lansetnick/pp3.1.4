package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;
import java.util.List;

@Controller
public class UserController {
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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

    @GetMapping("/ara")
    public String araPage(Principal principal, ModelMap model) {
        List<User> users = userService.findAll();
        List<String> role = roleService.findRoleName();
        model.addAttribute("messages", users);
        model.addAttribute("role", role);
        model.addAttribute("user", new User());
        model.addAttribute("userInfo", userService.findByUsername(principal.getName()));
        return "ara";
    }

    @GetMapping("/")
    public String index() {
        return "ara";
    }

    @GetMapping("/user")
    public String userPage(Principal principal, ModelMap model) {
        model.addAttribute("userInfo", userService.findByUsername(principal.getName()));
        return "user";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }


    @PatchMapping("ara/{id}")
    public String update(@ModelAttribute("edit") User user) {
        userService.changeUser(user);
        return "redirect:/ara";
    }

    @DeleteMapping("ara/{id}")
    public String newDeletedUser(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/ara";
    }

    @PostMapping("/ara")
    public String newCreateNewUser(@ModelAttribute("user") User user) {
        String pablo = passwordEncoder.encode(user.getPassword());
        user.setPassword(pablo);
        userService.saveUser(user);
        return "redirect:/ara";
    }
}
