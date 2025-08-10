package com.usta_backend.controller;

import com.usta_backend.dto.AdDto;
import com.usta_backend.dto.CreateAdRequest;
import com.usta_backend.dto.UpdateAdRequest;
import com.usta_backend.service.AdService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.nio.file.AccessDeniedException;
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
    @PutMapping("/{id}")
    public ResponseEntity<AdDto> update(@PathVariable Long id,
                                        @RequestBody UpdateAdRequest req,
                                        Authentication auth) throws AccessDeniedException {
        AdDto updated = adService.update(id, req, auth.getName()); // email текущего пользователя
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, Authentication auth) throws AccessDeniedException {
        adService.delete(id, auth.getName());
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/catalog")
    public ResponseEntity<Page<AdDto>> catalog(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) BigDecimal priceMin,
            @RequestParam(required = false) BigDecimal priceMax,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(required = false) String sort // пример: "price,asc"
    ) {
        return ResponseEntity.ok(
                adService.catalog(title, category, city, priceMin, priceMax, page, size, sort)
        );
    }
}
