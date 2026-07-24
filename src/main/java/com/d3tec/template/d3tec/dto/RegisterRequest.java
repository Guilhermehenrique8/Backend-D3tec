package com.d3tec.template.d3tec.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @Email
    @Schema(example = "usuario@usuario.com", description = "E-mail do usuÃ¡rio", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;
    @Size(min = 6, max = 64)
    @Schema(example = "senha123", description = "Senha do usuÃ¡rio", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    @NotBlank
    @Size(max = 150)
    @Schema(example = "Maria Silva", description = "Nome do usuário", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nome;
}
