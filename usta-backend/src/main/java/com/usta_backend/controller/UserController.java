package com.usta_backend.controller;

import com.usta_backend.model.Role;
import com.usta_backend.model.User;
import com.usta_backend.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }
    @PostMapping("/init-admin")
    public User initAdmin() {
        User admin = new User();
        admin.setEmail("admin@example.com");
        admin.setPassword("adminpass");
        admin.setPhone("1234324");
        admin.setName("Oleks");
        admin.setRole(Role.ADMIN);
        return userService.createUser(admin);
    }

}
