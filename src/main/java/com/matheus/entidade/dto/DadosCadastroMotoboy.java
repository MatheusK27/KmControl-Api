package com.matheus.entidade.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record DadosCadastroMotoboy (@NotBlank String nome,
                                    @NotBlank String cnh,
                                    String telefone) {
}
