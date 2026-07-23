package com.d3tec.template.d3tec.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "success_case")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Case implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(
            sequenceName = "success_case_id_seq",
            name = "success_case_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "success_case_id_seq"
    )
    private Long id;

    @Column(name = "nome_projeto", length = 150, nullable = false)
    private String nomeProjeto;

    @Column(length = 150)
    private String cliente;

    @Column(name = "categoria_servico", length = 100)
    private String categoriaServico;

    @Column(name = "contexto_problema", columnDefinition = "TEXT", nullable = false)
    private String contextoProblema;

    @Column(name = "solucao_desenvolvida", columnDefinition = "TEXT", nullable = false)
    private String solucaoDesenvolvida;

    @Column(name = "tecnologias_utilizadas", length = 500)
    private String tecnologiasUtilizadas;

    @Column(name = "resultado_obtido", columnDefinition = "TEXT")
    private String resultadoObtido;

    @Column(name = "imagem_capa", length = 255)
    private String imagemCapa;

    @Column(columnDefinition = "TEXT")
    private String depoimento;

    @Column(nullable = false)
    private boolean publicado = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
