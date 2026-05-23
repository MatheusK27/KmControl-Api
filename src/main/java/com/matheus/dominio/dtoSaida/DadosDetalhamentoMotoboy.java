package com.matheus.dominio.dtoSaida;

import com.matheus.dominio.entidades.Motoboy;

import java.util.List;

public record DadosDetalhamentoMotoboy(Long id, String nome, String cnh, String telefone, String placa) {

    public DadosDetalhamentoMotoboy (Motoboy motoboy){
        this(motoboy.getId(),motoboy.getNome(),motoboy.getCnh(),motoboy.getTelefone(),motoboy.getPlaca());

    }
}
