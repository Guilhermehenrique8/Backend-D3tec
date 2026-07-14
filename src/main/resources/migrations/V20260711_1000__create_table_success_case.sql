CREATE SEQUENCE success_case_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE success_case (
    id BIGINT NOT NULL DEFAULT nextval('success_case_id_seq'),
    nome_projeto VARCHAR(150) NOT NULL,
    cliente VARCHAR(150),
    categoria_servico VARCHAR(100),
    contexto_problema TEXT NOT NULL,
    solucao_desenvolvida TEXT NOT NULL,
    tecnologias_utilizadas VARCHAR(500),
    resultado_obtido TEXT,
    imagem_capa VARCHAR(255),
    depoimento TEXT,
    publicado BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL,

    CONSTRAINT pk_success_case PRIMARY KEY (id)
);