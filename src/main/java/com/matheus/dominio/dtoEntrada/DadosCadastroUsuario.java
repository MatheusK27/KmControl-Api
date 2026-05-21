package com.matheus.dominio.dtoEntrada;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroUsuario(@NotBlank String nome,
                                   @NotBlank String login,
                                   @NotBlank String senha) {
}
