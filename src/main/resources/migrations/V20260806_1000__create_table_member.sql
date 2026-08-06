CREATE SEQUENCE member_id_seq START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;

CREATE TABLE member (
    id BIGINT NOT NULL DEFAULT nextval('member_id_seq'),
    usuario_id BIGINT NOT NULL,
    cargo VARCHAR(100),
    instagram VARCHAR(255),
    github VARCHAR(255),
    linkedin VARCHAR(255),
    foto_perfil VARCHAR(255),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,

    CONSTRAINT pk_member PRIMARY KEY (id),
    CONSTRAINT uq_member_usuario UNIQUE (usuario_id),
    CONSTRAINT fk_member_usuario FOREIGN KEY (usuario_id) REFERENCES users(id)
);
