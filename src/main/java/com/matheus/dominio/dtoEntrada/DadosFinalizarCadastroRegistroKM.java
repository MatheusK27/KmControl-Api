package com.matheus.dominio.dtoEntrada;

import jakarta.validation.constraints.NotNull;

public record DadosFinalizarCadastroRegistroKM(@NotNull Integer kmSaidaAlmoco,
                                               @NotNull Integer kmRetornoAlmoco,
                                                @NotNull Integer kmFim) {
}
