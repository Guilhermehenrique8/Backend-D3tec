package com.d3tec.template.d3tec.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PartnerRequest {

    @NotBlank
    @Size(max = 150)
    private String nome;

    @Size(max = 255)
    private String logo;

    @Size(max = 255)
    private String link;

    @NotBlank
    @Size(max = 20)
    private String tipo;

    private boolean ativo;

    private boolean autorizacaoExibicao;
}
