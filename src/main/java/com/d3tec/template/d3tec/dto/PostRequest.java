package com.d3tec.template.d3tec.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

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
    @Size(max = 500)
    private String resumo;

    @NotBlank
    private String conteudo;

    @Size(max = 100)
    private String categoria;
}
