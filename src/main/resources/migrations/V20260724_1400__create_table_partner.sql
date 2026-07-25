CREATE SEQUENCE partner_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE partner (
    id BIGINT NOT NULL DEFAULT nextval('partner_id_seq'),
    nome VARCHAR(150) NOT NULL,
    logo VARCHAR(255),
    link VARCHAR(255),
    tipo VARCHAR(20) NOT NULL DEFAULT 'CLIENTE',
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    autorizacao_exibicao BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,

    CONSTRAINT pk_partner PRIMARY KEY (id)
);
