package com.matheus.entidades;


import com.matheus.entidades.dtoEntrada.DadosAtualizarRegistroKm;
import com.matheus.entidades.dtoEntrada.DadosCadastroRegistroKm;
import com.matheus.entidades.entidades.Motoboy;
import com.matheus.entidades.entidades.RegistroKm;
import com.matheus.infra.RegraDeNegocioException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class RegistroKmEntidadeTest {

    @Test
    void deveLancarErroQuandoKmEntradaForNull() {
        var dados = new DadosCadastroRegistroKm(1L, LocalDate.now(), null, null, null, null);
        var motoboy = mock(Motoboy.class);
        var erro = assertThrows(RegraDeNegocioException.class, () -> {
            new RegistroKm(dados, motoboy);
        });
        assertEquals("Km entrada não fornecido ou menor que zero", erro.getMessage());

    }

    @Test
    void deveLancarErroQuandoKmEntradaForZero() {
        var dados = new DadosCadastroRegistroKm(1L, LocalDate.now(), 0, null, null, null);
        var motoboy = mock(Motoboy.class);
        var erro =  assertThrows(RegraDeNegocioException.class, () -> {
            new RegistroKm(dados, motoboy);
        });
        assertEquals("Km entrada não fornecido ou menor que zero", erro.getMessage());

    }

    @Test
    void deveLancarErroQuandoDataNaoForDataAtual(){
        var dados = new DadosCadastroRegistroKm(1L, LocalDate.now().plusDays(1), 1000, 1100, 1200, 1300);
        var motoboy= mock(Motoboy.class);
        var erro = assertThrows(RegraDeNegocioException.class, () -> new RegistroKm(dados, motoboy));
        assertEquals( "Permitido somente data atual!", erro.getMessage());

    }

    @Test
    void deveLancarErroQuandoKmEntradaForNullValidarRegistroCompleto() {
        var dados = new DadosAtualizarRegistroKm(1L, 1L, LocalDate.now(), null, 1100, 1100, 1200);
        var registro = new RegistroKm();
        var erro = assertThrows(RegraDeNegocioException.class, () -> registro.atualizarKm(dados));
        assertEquals( "KM de entrada é obrigatório", erro.getMessage());

    }

    @Test
    void deveLancarErroQuandoKmSaidaAlmocoforMenorQueKmDeEntrada() {
        var dados = new DadosAtualizarRegistroKm(1L, 1L, LocalDate.now(), 1000, 900, 1100, 1200);
        var registro = new RegistroKm();
        var erro = assertThrows(RegraDeNegocioException.class, () -> registro.atualizarKm(dados));
        assertEquals("KM de saída para almoço não pode ser menor que KM de entrada", erro.getMessage());

    }

    @Test
    void deveLancarErroQuandoKmSaindaAlmocoForNullERetornoNaoForNull() {
        var dados = new DadosAtualizarRegistroKm(1L, 1L, LocalDate.now(), 1000, null, 1000, 1200);
        var registro = new RegistroKm();
        var erro = assertThrows(RegraDeNegocioException.class, () -> registro.atualizarKm(dados));
        assertEquals("KM de retorno do almoço exige KM de saída para almoço", erro.getMessage());
    }


    @Test
    void deveLancarErroQuandoKmRetornoAlmocoForMenorQueKmDeSaida() {
        var dados = new DadosAtualizarRegistroKm(1L, 1L, LocalDate.now(), 1000, 1100, 1000, 1200);
        var registro = new RegistroKm();
        var erro = assertThrows(RegraDeNegocioException.class, () -> registro.atualizarKm(dados));
        assertEquals("KM de retorno do almoço não pode ser menor que KM de saída para almoço", erro.getMessage());
    }

    @Test
    void deveLancarErroQuandoKmfinalForMenorQueKmAnterior() {
        var dados = new DadosAtualizarRegistroKm(1L, 1L, LocalDate.now(), 1100, 1200, 1200, 1150);
        var registro = new RegistroKm();
        var erro = assertThrows(RegraDeNegocioException.class, () -> registro.atualizarKm(dados));
        assertEquals("KM final não pode ser menor que o KM anterior", erro.getMessage());

    }

}



