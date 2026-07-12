package com.matheus.entidades.dtoEntrada;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DadosFinalizarCadastroRegistroKM(@NotNull Integer kmSaidaAlmoco,
                                               @NotNull Integer kmRetornoAlmoco,
                                               @NotNull Integer kmFim) {
}
