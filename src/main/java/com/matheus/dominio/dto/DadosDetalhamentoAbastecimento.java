package com.matheus.dominio.dto;

import com.matheus.dominio.entidades.Abastecimento;
import com.matheus.dominio.entidades.Motoboy;
import com.matheus.dominio.entidades.Usuario;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DadosDetalhamentoAbastecimento(Motoboy motoboyId, Integer kmMomento, LocalDate data,
                                                 BigDecimal valorLitro,BigDecimal valorTotal) {
    public DadosDetalhamentoAbastecimento(Abastecimento abastecimento) {
        this(abastecimento.getMotoboy(),abastecimento.getKmMomento(),abastecimento.getData(),
                abastecimento.getValorLitro(),abastecimento.getValorTotal());
    }
}
