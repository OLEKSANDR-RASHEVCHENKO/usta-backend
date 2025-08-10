package com.usta_backend.dto;

import lombok.*;
import java.math.BigDecimal;

@Data @NoArgsConstructor @AllArgsConstructor
public class CreateAdRequest {
    private String title;
    private String description;
    private BigDecimal price;
    private String category;
    private String city;
}
