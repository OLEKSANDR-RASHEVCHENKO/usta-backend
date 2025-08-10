package com.usta_backend.mapper;

import com.usta_backend.dto.AdDto;
import com.usta_backend.model.Ad;
import org.springframework.stereotype.Component;

@Component
public class AdMapper {
    public AdDto toDto(Ad ad) {
        return AdDto.builder()
                .id(ad.getId())
                .title(ad.getTitle())
                .description(ad.getDescription())
                .price(ad.getPrice())
                .category(ad.getCategory())
                .city(ad.getCity())
                .status(ad.getStatus().name())
                .ownerId(ad.getOwner().getId())
                .ownerEmail(ad.getOwner().getEmail())
                .build();
    }
}
