package com.matheus.dominio.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DadosAtualizarRegistroKm(@NotNull Long motoboyId,
                                       @NotNull LocalDate data,
                                       Integer kmInicio,
                                       Integer kmSaidaAlmoco,
                                       Integer kmRetornoAlmoco,
                                       Integer kmFim) {
}
