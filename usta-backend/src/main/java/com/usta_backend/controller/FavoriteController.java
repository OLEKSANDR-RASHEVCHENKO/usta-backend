package com.usta_backend.controller;

import com.usta_backend.dto.AdDto;
import com.usta_backend.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping("/{adId}")
    public ResponseEntity<Void> add(@PathVariable Long adId, Authentication auth) {
        favoriteService.add(adId, auth.getName());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{adId}")
    public ResponseEntity<Void> remove(@PathVariable Long adId, Authentication auth) {
        favoriteService.remove(adId, auth.getName());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<AdDto>> list(Authentication auth) {
        return ResponseEntity.ok(favoriteService.list(auth.getName()));
    }
}


