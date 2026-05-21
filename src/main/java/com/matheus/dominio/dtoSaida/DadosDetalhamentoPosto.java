package com.matheus.dominio.dtoSaida;


import com.matheus.dominio.entidades.Abastecimento;
import com.matheus.dominio.entidades.Posto;

import java.util.List;

public record DadosDetalhamentoPosto(Long Id, String nome) {
    public DadosDetalhamentoPosto(Posto posto) {
        this(posto.getId(),  posto.getNome());
    }


}
