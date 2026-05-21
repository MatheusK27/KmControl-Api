CREATE TABLE usuario (
                         id BIGSERIAL PRIMARY KEY,
                         nome VARCHAR(255) NOT NULL,
                         login VARCHAR(255) NOT NULL UNIQUE,
                         senha VARCHAR(255) NOT NULL,
                         tipo_usuario VARCHAR(50),
                         ativo BOOLEAN NOT NULL DEFAULT TRUE,
                         criado_em TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE motoboy (
                         id BIGSERIAL PRIMARY KEY,
                         nome VARCHAR(255),
                         cnh VARCHAR(255),
                         placa VARCHAR(255),
                         telefone VARCHAR(255),
                         ativo BOOLEAN DEFAULT TRUE,
                         criado_em TIMESTAMP DEFAULT NOW()
);

CREATE TABLE abastecimento (
                               id BIGSERIAL PRIMARY KEY,
                               motoboy_id BIGINT NOT NULL REFERENCES motoboy(id),
                               usuario_id BIGINT NOT NULL REFERENCES usuario(id),
                               data DATE DEFAULT CURRENT_DATE,
                               km_momento INTEGER,
                               litros NUMERIC(10,2),
                               valor_litro NUMERIC(10,2),
                               valor_total NUMERIC(10,2),
                               posto VARCHAR(255),
                               criado_em TIMESTAMP DEFAULT NOW()
);

CREATE TABLE registro_km (
                             id BIGSERIAL PRIMARY KEY,
                             motoboy_id BIGINT NOT NULL REFERENCES motoboy(id),
                             data DATE DEFAULT CURRENT_DATE,
                             km_entrada INTEGER,
                             km_saida_almoco INTEGER,
                             km_retorno_almoco INTEGER,
                             km_fim INTEGER,
                             observacao VARCHAR(255),
                             criado_em TIMESTAMP DEFAULT NOW()
);