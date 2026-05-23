package com.matheus.dominio.dtoEntrada;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizarMotoboy(@NotNull Long id,
                                     String nome,
                                    String cnh,
                                    String telefone,
                                    String placa
                                     ) {
}
