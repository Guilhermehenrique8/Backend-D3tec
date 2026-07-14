package com.d3tec.template.nomeDoSeuProjeto.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ContactRequest {

    @NotBlank
    @Size(max = 150)
    @Schema(example = "Maria Silva", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nome;

    @NotBlank
    @Email
    @Schema(example = "maria@empresa.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @Size(max = 30)
    private String telefone;

    @Size(max = 150)
    private String empresa;

    @NotBlank
    @Size(max = 150)
    @Schema(example = "Orçamento para sistema web", requiredMode = Schema.RequiredMode.REQUIRED)
    private String assunto;

    @NotBlank
    @Size(max = 5000)
    private String mensagem;

    // Honeypot anti-spam: campo escondido no formulário do site.
    // Um visitante real nunca preenche isso; bots costumam preencher tudo.
    private String website;
}