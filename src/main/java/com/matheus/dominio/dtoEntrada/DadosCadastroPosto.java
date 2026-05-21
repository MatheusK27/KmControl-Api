package com.matheus.dominio.dtoEntrada;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroPosto(Long Id, @NotBlank String nome) {
}
