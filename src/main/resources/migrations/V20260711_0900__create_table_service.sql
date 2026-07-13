CREATE SEQUENCE service_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE service (
    id BIGINT NOT NULL DEFAULT nextval('service_id_seq'), -- MUDOU AQUI
    nome VARCHAR(150) NOT NULL,
    descricao_curta VARCHAR(300) NOT NULL,
    descricao_detalhada TEXT NOT NULL,
    problemas_que_resolve TEXT,
    beneficios TEXT,
    icone VARCHAR(255),
    cta_texto VARCHAR(100),
    cta_link VARCHAR(255),
    created_at TIMESTAMP NOT NULL,

    CONSTRAINT pk_service PRIMARY KEY (id)
);