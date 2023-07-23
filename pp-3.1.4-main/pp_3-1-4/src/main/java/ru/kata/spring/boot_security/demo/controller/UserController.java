package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/index")
    public String indexPage(Principal principal, ModelMap model) {
        List<User> users = userService.findAll();
        List<String> role = roleService.findRoleName();
        model.addAttribute("messages", users);
        model.addAttribute("role", role);
        model.addAttribute("user", new User());
        model.addAttribute("userInfo", userService.findByUsername(principal.getName()));
        return "index";
    }

    @GetMapping("/")
    public String index() {
        return "index";
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


    @PatchMapping("index/{id}")
    public String update(@ModelAttribute("edit") User user) {
        userService.changeUser(user);
        return "redirect:/index";
    }

    @DeleteMapping("index/{id}")
    public String newDeletedUser(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/index";
    }

    @PostMapping("/index")
    public String newCreateNewUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/index";
    }
}
