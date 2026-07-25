CREATE SEQUENCE indicador_id_seq START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;

CREATE TABLE indicador (
    id BIGINT NOT NULL DEFAULT nextval('indicador_id_seq'),
    nome VARCHAR(150) NOT NULL,
    valor VARCHAR(50) NOT NULL,
    descricao VARCHAR(300),
    updated_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL,
    CONSTRAINT pk_indicador PRIMARY KEY (id)
);
