package com.usta_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAdRequest {
    private String title;        // опционально
    private String description;  // опционально
    private BigDecimal price;    // опционально
    private String category;     // опционально
    private String city;         // опционально
    private String status;       // опционально: менять может только ADMIN (ACTIVE/HIDDEN/ARCHIVED и т.п.)
}
