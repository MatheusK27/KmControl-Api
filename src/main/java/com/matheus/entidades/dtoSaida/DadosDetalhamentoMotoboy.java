package com.matheus.entidades.dtoSaida;

import com.matheus.entidades.entidades.Motoboy;

public record DadosDetalhamentoMotoboy(Long id, String nome, String cnh, String telefone, String placa) {

    public DadosDetalhamentoMotoboy (Motoboy motoboy){
        this(motoboy.getId(),motoboy.getNome(),motoboy.getCnh(),motoboy.getTelefone(),motoboy.getPlaca());

    }
}
