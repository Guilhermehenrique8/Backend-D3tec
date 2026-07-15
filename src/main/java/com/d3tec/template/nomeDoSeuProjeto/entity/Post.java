package com.d3tec.template.nomeDoSeuProjeto.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "post")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Post implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(
            sequenceName = "post_id_seq",
            name = "post_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "post_id_seq"
    )
    private Long id;

    @Column(length = 200, nullable = false)
    private String titulo;

    @Column(length = 220, nullable = false, unique = true)
    private String slug;

    @Column(length = 150, nullable = false)
    private String autor;

    @Column(name = "imagem_capa", length = 255)
    private String imagemCapa;

    @Column(length = 500, nullable = false)
    private String resumo;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String conteudo;

    @Column(length = 100)
    private String categoria;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private PostStatus status = PostStatus.RASCUNHO;

    @Column(name = "published_at")
    private LocalDateTime dataPublicacao;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}