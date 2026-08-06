package com.d3tec.template.d3tec.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MemberRequest {

    @NotBlank @Size(max = 150)
    private String nome;

    @NotBlank @Email
    private String email;
    private String password;

    @NotBlank
    private String role;

    @Size(max = 100)
    private String cargo;

    @Size(max = 255)
    private String instagram;

    @Size(max = 255)
    private String github;

    @Size(max = 255)
    private String linkedin;

    @Size(max = 255)
    private String fotoPerfil;

    private boolean exibirAoPublico;
}
