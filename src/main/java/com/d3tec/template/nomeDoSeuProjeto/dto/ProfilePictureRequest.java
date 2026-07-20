package com.d3tec.template.nomeDoSeuProjeto.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProfilePictureRequest {
    
    @NotBlank
    private String profilePictureUrl;
}
