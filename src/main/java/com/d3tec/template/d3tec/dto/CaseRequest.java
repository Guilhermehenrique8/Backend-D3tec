package com.d3tec.template.d3tec.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.List;

@Data
public class CaseRequest {

    @NotBlank
    @Size(max = 150)
    private String nomeProjeto;

    @Size(max = 150)
    private String cliente;

    @NotBlank
    private String descricao;

    @Size(max = 255)
    private String imagemCapa;

    private String depoimento;

    private List<Long> tagIds;

    private boolean exibirAoPublico;

    private boolean featured;
}
