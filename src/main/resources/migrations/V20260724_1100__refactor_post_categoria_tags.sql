CREATE SEQUENCE IF NOT EXISTS categoria_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE categoria (
    id BIGINT NOT NULL DEFAULT nextval('categoria_id_seq'),
    nome VARCHAR(100) NOT NULL,
    slug VARCHAR(120) NOT NULL,

    CONSTRAINT pk_categoria PRIMARY KEY (id),
    CONSTRAINT uq_categoria_nome UNIQUE (nome),
    CONSTRAINT uq_categoria_slug UNIQUE (slug)
);

INSERT INTO categoria (id, nome, slug) VALUES
    (nextval('categoria_id_seq'), 'Tecnologia', 'tecnologia'),
    (nextval('categoria_id_seq'), 'Seguranca', 'seguranca'),
    (nextval('categoria_id_seq'), 'Desenvolvimento', 'desenvolvimento'),
    (nextval('categoria_id_seq'), 'Inteligencia Artificial', 'inteligencia-artificial'),
    (nextval('categoria_id_seq'), 'Infraestrutura', 'infraestrutura'),
    (nextval('categoria_id_seq'), 'Gestao', 'gestao');

ALTER TABLE post
    DROP CONSTRAINT IF EXISTS fk_post_tag;

DROP INDEX IF EXISTS idx_post_status;

ALTER TABLE post
    DROP COLUMN IF EXISTS tag_id,
    DROP COLUMN IF EXISTS resumo,
    DROP COLUMN IF EXISTS conteudo,
    DROP COLUMN IF EXISTS categoria,
    DROP COLUMN IF EXISTS status;

ALTER TABLE post
    ADD COLUMN IF NOT EXISTS descricao TEXT,
    ADD COLUMN IF NOT EXISTS categoria_id BIGINT,
    ADD COLUMN IF NOT EXISTS exibir_ao_publico BOOLEAN NOT NULL DEFAULT false;

UPDATE post SET descricao = '' WHERE descricao IS NULL;

ALTER TABLE post
    ALTER COLUMN descricao SET NOT NULL;

ALTER TABLE post
    ADD CONSTRAINT fk_post_categoria FOREIGN KEY (categoria_id) REFERENCES categoria(id);

CREATE TABLE post_tags (
    post_id BIGINT NOT NULL,
    tag_id BIGINT NOT NULL,

    CONSTRAINT pk_post_tags PRIMARY KEY (post_id, tag_id),
    CONSTRAINT fk_post_tags_post FOREIGN KEY (post_id) REFERENCES post(id) ON DELETE CASCADE,
    CONSTRAINT fk_post_tags_tag FOREIGN KEY (tag_id) REFERENCES tag(id) ON DELETE CASCADE
);
