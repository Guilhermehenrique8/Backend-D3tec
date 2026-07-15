CREATE SEQUENCE post_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE post (
    id BIGINT NOT NULL DEFAULT nextval('post_id_seq'),
    titulo VARCHAR(200) NOT NULL,
    slug VARCHAR(220) NOT NULL,
    autor VARCHAR(150),
    imagem_capa VARCHAR(255),
    resumo VARCHAR(500) NOT NULL,
    conteudo TEXT NOT NULL,
    categoria VARCHAR(100),
    status VARCHAR(20) NOT NULL DEFAULT 'RASCUNHO',
    published_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,

    CONSTRAINT pk_post PRIMARY KEY (id),
    CONSTRAINT uq_post_slug UNIQUE (slug)
);

CREATE INDEX idx_post_status ON post(status);