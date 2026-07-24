CREATE SEQUENCE tag_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE tag (
    id BIGINT NOT NULL DEFAULT nextval('tag_id_seq'),
    nome VARCHAR(100) NOT NULL,

    CONSTRAINT pk_tag PRIMARY KEY (id),
    CONSTRAINT uq_tag_nome UNIQUE (nome)
);

ALTER TABLE post
    ADD COLUMN tag_id BIGINT;

ALTER TABLE post
    ADD CONSTRAINT fk_post_tag FOREIGN KEY (tag_id) REFERENCES tag(id);