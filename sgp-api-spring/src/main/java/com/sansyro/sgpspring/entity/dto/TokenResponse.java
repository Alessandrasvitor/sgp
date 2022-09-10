package com.sansyro.sgpspring.entity.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenResponse {
    private String type;
    private String token;
}
