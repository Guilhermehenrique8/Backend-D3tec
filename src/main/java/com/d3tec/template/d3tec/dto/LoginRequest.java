package com.d3tec.template.d3tec.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginRequest {
    @NotEmpty(message = "O email nÃ£o pode estar vazio!")
    @Email(message = "Formato de email invÃ¡lido")
    @Schema(example = "admin@admin.com", description = "E-mail do usuÃ¡rio")
    private String email;
    @NotEmpty(message = "A senha nÃ£o deve estar vazia")
    @Schema(example = "Admin@123", description = "Senha do usuÃ¡rio")
    private String password;
}
