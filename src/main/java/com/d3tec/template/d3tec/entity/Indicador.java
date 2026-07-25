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
@Table(name = "indicador")
@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class Indicador implements Serializable {
    @Serial private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(sequenceName = "indicador_id_seq", name = "indicador_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "indicador_id_seq")
    private Long id;

    @Column(length = 150, nullable = false)
    private String nome;

    @Column(length = 50, nullable = false)
    private String valor;

    @Column(length = 300)
    private String descricao;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
