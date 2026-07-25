package com.d3tec.template.d3tec.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class IndicadorRequest {
    @NotBlank @Size(max = 150)
    private String nome;
    @NotBlank @Size(max = 50)
    private String valor;
    @Size(max = 300)
    private String descricao;
}
