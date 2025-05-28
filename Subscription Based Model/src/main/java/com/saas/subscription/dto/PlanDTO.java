package com.saas.subscription.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanDTO {
    private Long id;
    private String name;
    private BigDecimal price;
    private List<String> features;
    private Integer duration;
    private String description;
} 