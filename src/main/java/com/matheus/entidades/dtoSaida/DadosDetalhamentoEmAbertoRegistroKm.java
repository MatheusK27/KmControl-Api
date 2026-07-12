package com.matheus.entidades.dtoSaida;

import com.matheus.entidades.entidades.RegistroKm;

import java.time.LocalDate;

public record DadosDetalhamentoEmAbertoRegistroKm(Long id,
                                                  Long motoboyId,
                                                  String motoboyNome,
                                                  LocalDate data,
                                                  Integer kmEntrada,
                                                  Integer kmSaidaAlmoco,
                                                  Integer KmRetornoAlmoco) {

    public DadosDetalhamentoEmAbertoRegistroKm(RegistroKm km){
        this(km.getId(),km.getMotoboy().getId(), km.getMotoboy().getNome(),km.getData(), km.getKmEntrada(), km.getKmSaidaAlmoco(), km.getKmRetornoAlmoco());
    }

}

