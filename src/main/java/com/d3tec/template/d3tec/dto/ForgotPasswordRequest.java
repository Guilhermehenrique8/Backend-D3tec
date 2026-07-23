package com.d3tec.template.d3tec.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ForgotPasswordRequest {
    @NotBlank(message = "O email nÃ£o pode estar vazio!")
    @Email(message = "Formato de email invÃ¡lido")
    private String email;
}
