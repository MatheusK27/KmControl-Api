package com.matheus.dominio.dtoEntrada;



import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DadosCadastroRegistroKm( @NotNull Long motoboyId,
                                      @NotNull LocalDate data,
                                      Integer kmEntrada,
                                      Integer kmSaidaAlmoco,
                                      Integer kmRetornoAlmoco,
                                      Integer kmFim) {
}
