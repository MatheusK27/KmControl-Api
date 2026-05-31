package com.matheus.entidades.dtoSaida;

import com.matheus.entidades.entidades.RegistroKm;

import java.time.LocalDate;

public record DadosDetalhamentoRegistroKm (
                                           Long motoboyId,
                                           LocalDate data,
                                           Integer kmInicio,
                                           Integer kmSaidaAlmoco,
                                           Integer kmRetornoAlmoco,
                                           Integer kmFim){

    public DadosDetalhamentoRegistroKm(RegistroKm km) {
        this(km.getMotoboy().getId(),km.getData(),km.getKmEntrada(),km.getKmSaidaAlmoco(), km.getKmRetornoAlmoco(), km.getKmFim());
    }


}
