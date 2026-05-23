package com.matheus.dominio.dtoEntrada;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroPosto(Long Id,
                                 @NotBlank String nome,
                                 @NotBlank String cnpj,
                                 @NotBlank String telefone,
                                 @NotBlank String email,
                                 @NotBlank String cidade) {
}
