CREATE SEQUENCE contact_message_seq_generator
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE contact_message (
    id BIGINT NOT NULL DEFAULT nextval('contact_message_seq_generator'),
    nome VARCHAR(150) NOT NULL,
    email VARCHAR(255) NOT NULL,
    telefone VARCHAR(30),
    empresa VARCHAR(150),
    assunto VARCHAR(150) NOT NULL,
    mensagem TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL,

    CONSTRAINT pk_contact_message PRIMARY KEY (id)
);

CREATE INDEX idx_contact_message_created_at
    ON contact_message(created_at);