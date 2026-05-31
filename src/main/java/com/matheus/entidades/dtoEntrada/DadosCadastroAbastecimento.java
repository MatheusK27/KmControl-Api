package com.matheus.entidades.dtoEntrada;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;


public record DadosCadastroAbastecimento(
        @NotNull Long motoboyId,
        @NotNull Long usuarioId,
        @NotNull LocalDate data,
        @NotNull Integer kmMomento,
        @NotNull BigDecimal litros,
        @NotNull BigDecimal valorLitro,
        @NotNull Long postoId
        ) {
}
