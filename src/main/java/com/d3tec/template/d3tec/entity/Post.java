package com.d3tec.template.d3tec.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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

    @Column(length = 150)
    private String autor;

    @Column(name = "imagem_capa", length = 255)
    private String imagemCapa;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @ManyToMany
    @JoinTable(
            name = "post_tags",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    @Column(name = "exibir_ao_publico", nullable = false)
    private boolean exibirAoPublico;

    @Column(name = "published_at")
    private LocalDateTime dataPublicacao;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
