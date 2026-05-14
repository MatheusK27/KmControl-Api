package com.matheus.dominio.dto;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroUsuario(@NotBlank String nome,
                                   @NotBlank String login,
                                   @NotBlank String senha) {
}
