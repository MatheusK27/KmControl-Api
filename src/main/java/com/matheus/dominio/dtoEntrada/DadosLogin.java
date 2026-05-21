package com.matheus.dominio.dtoEntrada;

import jakarta.validation.constraints.NotBlank;

public record DadosLogin(@NotBlank String login, @NotBlank String senha ) {

}
