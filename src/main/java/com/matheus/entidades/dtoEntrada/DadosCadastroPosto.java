package com.matheus.entidades.dtoEntrada;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroPosto(
                                 @NotBlank String nome,
                                 @NotBlank String cnpj,
                                 @NotBlank String telefone,
                                 @NotBlank String email,
                                 @NotBlank String cidade) {
}
