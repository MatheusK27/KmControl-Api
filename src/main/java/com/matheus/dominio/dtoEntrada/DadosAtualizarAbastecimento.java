package com.matheus.dominio.dtoEntrada;
import java.math.BigDecimal;
import java.time.LocalDate;

public record DadosAtualizarAbastecimento(
                                           Long motoboyId,
                                           Long usuarioId,
                                           LocalDate data,
                                           Integer kmMomento,
                                           BigDecimal litros,
                                           BigDecimal valorLitro,
                                           String posto) {

}
