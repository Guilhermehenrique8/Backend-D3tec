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
@Table(name = "partner")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Partner implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(sequenceName = "partner_id_seq", name = "partner_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "partner_id_seq")
    private Long id;

    @Column(length = 150, nullable = false)
    private String nome;

    @Column(length = 255)
    private String logo;

    @Column(length = 255)
    private String link;

    @Column(length = 20, nullable = false)
    private String tipo;

    @Column(nullable = false)
    private boolean ativo = true;

    @Column(name = "autorizacao_exibicao", nullable = false)
    private boolean autorizacaoExibicao;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
