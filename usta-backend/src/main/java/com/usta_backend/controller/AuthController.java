package com.usta_backend.controller;

import com.usta_backend.mapper.UserMapper;
import com.usta_backend.model.User;
import com.usta_backend.service.UserService;
import com.usta_backend.dto.RegisterRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class AuthController {

    private final UserService userService;
    private final UserMapper userMapper;

    public AuthController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        User created = userService.register(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toDto(created));
    }
}

