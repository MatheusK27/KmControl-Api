package com.matheus.dominio.dtoEntrada;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DadosAtualizarRegistroKm(@NotNull Long id,
                                       Long motoboyId,
                                       LocalDate data,
                                       Integer kmInicio,
                                       Integer kmSaidaAlmoco,
                                       Integer kmRetornoAlmoco,
                                       Integer kmFim) {
}
