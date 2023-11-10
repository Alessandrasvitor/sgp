package com.sansyro.sgpspring.entity.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenResponse {
    private String type;
    private String token;
    private UserDTO user;
}
