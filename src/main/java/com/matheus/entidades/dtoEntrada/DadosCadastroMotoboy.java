package com.matheus.entidades.dtoEntrada;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroMotoboy (
                                    @NotBlank String nome,
                                    @NotBlank String cnh,
                                    String telefone,
                                    @NotBlank String placa) {
}
