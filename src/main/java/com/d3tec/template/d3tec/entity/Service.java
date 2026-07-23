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
@Table(name = "service")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Service implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(
            sequenceName = "service_id_seq",
            name = "service_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "service_id_seq"
    )
    private Long id;

    @Column(length = 150, nullable = false)
    private String nome;

    @Column(name = "descricao_curta", length = 300, nullable = false)
    private String descricaoCurta;

    @Column(name = "descricao_detalhada", columnDefinition = "TEXT", nullable = false)
    private String descricaoDetalhada;

    @Column(name = "problemas_que_resolve", columnDefinition = "TEXT")
    private String problemasQueResolve;

    @Column(columnDefinition = "TEXT")
    private String beneficios;

    @Column(length = 255)
    private String icone;

    @Column(name = "cta_texto", length = 100)
    private String ctaTexto;

    @Column(name = "cta_link", length = 255)
    private String ctaLink;

    private LocalDateTime createdAt = LocalDateTime.now();
}
