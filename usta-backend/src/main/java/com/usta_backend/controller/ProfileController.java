package com.usta_backend.controller;


import com.usta_backend.userDto.UserDto;
import com.usta_backend.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping
    public ResponseEntity<UserDto> getCurrentUser(Authentication authentication) {
        String email = authentication.getName(); // Spring Security автоматично передає email користувача
        UserDto user = profileService.getCurrentUser(email);
        return ResponseEntity.ok(user);
    }
}
