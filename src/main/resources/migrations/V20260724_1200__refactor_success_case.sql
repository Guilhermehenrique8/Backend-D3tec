ALTER TABLE success_case
    DROP COLUMN IF EXISTS categoria_servico,
    DROP COLUMN IF EXISTS contexto_problema,
    DROP COLUMN IF EXISTS solucao_desenvolvida,
    DROP COLUMN IF EXISTS tecnologias_utilizadas,
    DROP COLUMN IF EXISTS resultado_obtido,
    DROP COLUMN IF EXISTS publicado;

ALTER TABLE success_case
    ADD COLUMN IF NOT EXISTS descricao TEXT,
    ADD COLUMN IF NOT EXISTS exibir_ao_publico BOOLEAN NOT NULL DEFAULT false,
    ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP;

UPDATE success_case SET descricao = '' WHERE descricao IS NULL;

ALTER TABLE success_case
    ALTER COLUMN descricao SET NOT NULL;

CREATE TABLE IF NOT EXISTS case_tags (
    case_id BIGINT NOT NULL,
    tag_id BIGINT NOT NULL,

    CONSTRAINT pk_case_tags PRIMARY KEY (case_id, tag_id),
    CONSTRAINT fk_case_tags_case FOREIGN KEY (case_id) REFERENCES success_case(id) ON DELETE CASCADE,
    CONSTRAINT fk_case_tags_tag FOREIGN KEY (tag_id) REFERENCES tag(id) ON DELETE CASCADE
);
