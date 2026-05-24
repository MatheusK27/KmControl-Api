package com.matheus.dominio.dtoSaida;


import com.matheus.dominio.entidades.Abastecimento;
import com.matheus.dominio.entidades.Motoboy;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DadosDetalhamentoAbastecimento(Integer kmMomento, LocalDate data,
                                             BigDecimal valorLitro, Long idMotoboy,Long idPosto, BigDecimal valorCombustivel) {

    public DadosDetalhamentoAbastecimento(Abastecimento abastecimento) {
        this(abastecimento.getKmMomento(),abastecimento.getData(),
                abastecimento.getValorLitro(),abastecimento.getMotoboy().getId(),abastecimento.getPosto().getId(),
                abastecimento.calculoCombustivel());
    }


}
