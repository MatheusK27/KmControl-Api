package com.matheus.entidades.dtoSaida;


import com.matheus.entidades.entidades.Abastecimento;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DadosDetalhamentoAbastecimento(Long id,Integer kmMomento, LocalDate data,
                                             BigDecimal valorLitro, BigDecimal litros, Long motoboyId,Long postoId, BigDecimal valorTotal) {

    public DadosDetalhamentoAbastecimento(Abastecimento abastecimento) {
        this(abastecimento.getId(), abastecimento.getKmMomento(),abastecimento.getData(),
                abastecimento.getValorLitro(),abastecimento.getLitros(),abastecimento.getMotoboy().getId(),abastecimento.getPosto().getId(),
                abastecimento.calculoCombustivel());
    }


}
