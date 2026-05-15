package com.matheus.dominio.dto;

import com.matheus.dominio.entidades.Motoboy;

public record DadosDetalhamentoMotoboy(Long id, String nome, String cnh, String telefone) {

    public DadosDetalhamentoMotoboy (Motoboy motoboy){
        this(motoboy.getId(),motoboy.getNome(),motoboy.getCnh(),motoboy.getTelefone());

    }
}
