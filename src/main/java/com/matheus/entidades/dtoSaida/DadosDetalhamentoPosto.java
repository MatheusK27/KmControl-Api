package com.matheus.entidades.dtoSaida;


import com.matheus.entidades.entidades.Posto;

public record DadosDetalhamentoPosto(Long Id, String nome,String telefone) {
    public DadosDetalhamentoPosto(Posto posto) {
        this(posto.getId(),  posto.getNome(),posto.getTelefone());
    }

}
