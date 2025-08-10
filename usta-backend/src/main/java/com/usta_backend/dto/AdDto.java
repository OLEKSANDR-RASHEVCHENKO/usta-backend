package com.usta_backend.dto;

import lombok.*;
import java.math.BigDecimal;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class AdDto {
    private Long id;
    private String title;
    private String description;
    private BigDecimal price;
    private String category;
    private String city;
    private String status;   // строкой
    private Long ownerId;
    private String ownerEmail;
}
