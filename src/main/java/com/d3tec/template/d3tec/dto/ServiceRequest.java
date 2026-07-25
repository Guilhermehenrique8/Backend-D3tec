package com.d3tec.template.d3tec.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ServiceRequest {

    @NotBlank
    @Size(max = 150)
    private String nome;

    @NotBlank
    @Size(max = 300)
    private String descricaoCurta;

    @NotBlank
    private String descricaoDetalhada;

    private String problemasQueResolve;

    private String beneficios;

    @Size(max = 255)
    private String icone;

    @Size(max = 100)
    private String ctaTexto;

    @Size(max = 255)
    private String ctaLink;
}
