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
@Table(name = "member")
@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class Member implements Serializable {
    @Serial private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(sequenceName = "member_id_seq", name = "member_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_id_seq")
    private Long id;

    @OneToOne
    @JoinColumn(name = "usuario_id", unique = true, nullable = false)
    private User usuario;

    @Column(length = 100)
    private String cargo;

    @Column(length = 255)
    private String instagram;

    @Column(length = 255)
    private String github;

    @Column(length = 255)
    private String linkedin;

    @Column(name = "foto_perfil", length = 255)
    private String fotoPerfil;

    @Column(name = "exibir_ao_publico", nullable = false)
    private boolean exibirAoPublico;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
