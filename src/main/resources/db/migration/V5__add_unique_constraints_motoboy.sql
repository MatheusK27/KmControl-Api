ALTER TABLE motoboy
    ADD CONSTRAINT uk_motoboy_placa UNIQUE (placa);

ALTER TABLE motoboy
    ADD CONSTRAINT uk_motoboy_cnh UNIQUE (cnh);