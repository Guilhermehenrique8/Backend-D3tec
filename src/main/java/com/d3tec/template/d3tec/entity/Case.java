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

    @Column(columnDefinition = "TEXT", nullable = false)
    private String descricao;

    @Column(name = "imagem_capa", length = 255)
    private String imagemCapa;

    @Column(columnDefinition = "TEXT")
    private String depoimento;

    @ManyToMany
    @JoinTable(
            name = "case_tags",
            joinColumns = @JoinColumn(name = "case_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    @Column(name = "exibir_ao_publico", nullable = false)
    private boolean exibirAoPublico;

    @Column(nullable = false)
    private boolean featured;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
