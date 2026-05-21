package com.matheus.dominio.dtoSaida;

import com.matheus.dominio.entidades.RegistroKm;

import java.time.LocalDate;

public record DadosDetalhamentoRegistroKm (
                                            LocalDate data,
                                           Integer kmInicio,
                                           Integer kmSaidaAlmoco,
                                           Integer kmRetornoAlmoco,
                                           Integer kmFim){

    public DadosDetalhamentoRegistroKm(RegistroKm km) {
        this(km.getData(),km.getKmEntrada(),km.getKmSaidaAlmoco(), km.getKmRetornoAlmoco(), km.getKmFim());
    }

}
