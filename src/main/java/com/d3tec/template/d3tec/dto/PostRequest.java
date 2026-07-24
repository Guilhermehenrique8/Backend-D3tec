package com.d3tec.template.d3tec.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.List;

@Data
public class PostRequest {

    @NotBlank
    @Size(max = 200)
    private String titulo;

    @NotBlank
    @Size(max = 150)
    private String autor;

    @Size(max = 255)
    private String imagemCapa;

    @NotBlank
    private String descricao;

    private Long categoriaId;

    private List<Long> tagIds;

    private boolean exibirAoPublico;
}
