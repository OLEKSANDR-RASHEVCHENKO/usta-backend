package com.usta_backend.controller;

import com.usta_backend.dto.AdDto;
import com.usta_backend.dto.CreateAdRequest;
import com.usta_backend.service.AdService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ads")
public class AdController {

    private final AdService adService;
    public AdController(AdService adService) { this.adService = adService; }

    // Публичный список
    @GetMapping
    public List<AdDto> list() { return adService.listActive(); }

    // Публичная карточка
    @GetMapping("/{id}")
    public AdDto get(@PathVariable Long id) { return adService.getById(id); }

    // Создать — только авторизованный
    @PostMapping
    public ResponseEntity<AdDto> create(@RequestBody CreateAdRequest req, Authentication auth) {
        AdDto created = adService.create(req, auth.getName()); // auth.getName() = email
        return ResponseEntity.ok(created);
    }
}
