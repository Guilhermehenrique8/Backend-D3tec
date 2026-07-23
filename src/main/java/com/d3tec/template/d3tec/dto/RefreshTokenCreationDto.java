package com.d3tec.template.d3tec.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class RefreshTokenCreationDto {
    private String rawToken;
    private Instant expiresAt;
}
