package com.d3tec.template.d3tec.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProfilePictureRequest {
    
    @NotBlank
    private String profilePictureUrl;
}
