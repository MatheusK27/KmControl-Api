package com.matheus.dominio.dto;

import com.matheus.dominio.entidades.Motoboy;
import com.matheus.dominio.entidades.RegistroKm;
import jakarta.validation.constraints.NotNull;

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
