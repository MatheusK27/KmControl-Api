package com.matheus.dominio.dto;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroMotoboy (@NotBlank String nome,
                                    @NotBlank String cnh,
                                    String telefone) {
}
