CREATE TABLE posto (
                       id BIGSERIAL PRIMARY KEY,
                       nome VARCHAR(255) NOT NULL,
                       ativo BOOLEAN DEFAULT TRUE,
                       criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE abastecimento
    ADD COLUMN posto_id BIGINT;

ALTER TABLE abastecimento
    ADD CONSTRAINT fk_abastecimento_posto
        FOREIGN KEY (posto_id)
            REFERENCES posto(id);

ALTER TABLE abastecimento
DROP COLUMN posto;