package com.matheus.entidades;


import com.matheus.entidades.dtoEntrada.DadosCadastroAbastecimento;
import com.matheus.entidades.entidades.*;
import com.matheus.infra.RegraDeNegocioException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;


@ExtendWith(MockitoExtension.class)
public class AbastecimentoEntidadeTest {

    @Test
    void deveLancarExcecaoQuandoLitrosForMaiorQueOito() {
        var dados = new DadosCadastroAbastecimento(
                1L, 1L, LocalDate.now(), 1500, BigDecimal.valueOf(9), BigDecimal.ONE, 1L);
        var motoboy = mock(Motoboy.class);
        var posto = mock(Posto.class);
        var usuario = mock(Usuario.class);
        assertThrows(RegraDeNegocioException.class, () -> new Abastecimento(dados, motoboy, usuario, posto));
    }

    @Test
    void naoDeveCadastrarQuandoLitroForIgualAZero() {
        var dados = new DadosCadastroAbastecimento(
                1L, 1L, LocalDate.now(), 1500, BigDecimal.valueOf(0), BigDecimal.ONE, 1L);
        var motoboy = mock(Motoboy.class);
        var posto = mock(Posto.class);
        var usuario = mock(Usuario.class);
        assertThrows(RegraDeNegocioException.class, () -> new Abastecimento(dados, motoboy, usuario, posto));
    }

    @Test
    void naoDeveCadastrarQuandoLitroForNegativo() {
        var dados = new DadosCadastroAbastecimento(
                1L, 1L, LocalDate.now(), 1500, BigDecimal.valueOf(-1), BigDecimal.ONE, 1L);
        var motoboy = mock(Motoboy.class);
        var posto = mock(Posto.class);
        var usuario = mock(Usuario.class);
        assertThrows(RegraDeNegocioException.class, () -> new Abastecimento(dados, motoboy, usuario, posto));


    }

    @Test
    void naoDeveCadastrarQuandoDataForDiferenteDeDataAtual() {
        var dados = new DadosCadastroAbastecimento(
                1L, 1L, LocalDate.now().plusDays(1), 1500, BigDecimal.valueOf(7), BigDecimal.ONE, 1L);
        var motoboy = mock(Motoboy.class);
        var posto = mock(Posto.class);
        var usuario = mock(Usuario.class);
        assertThrows(RegraDeNegocioException.class, () -> new Abastecimento(dados, motoboy, usuario, posto));

    }

    @Test
    void naoDeveCadastrarQuandoValorLitroForIgualAZero() {
        var dados = new DadosCadastroAbastecimento(
                1L, 1L, LocalDate.now(), 1500, BigDecimal.valueOf(7), BigDecimal.valueOf(0), 1L);
        var motoboy = mock(Motoboy.class);
        var posto = mock(Posto.class);
        var usuario = mock(Usuario.class);
        assertThrows(RegraDeNegocioException.class, () -> new Abastecimento(dados, motoboy, usuario, posto));
    }

    @Test
    void naoDeveCadastrarQuandoValorLitroForNegativo() {
        var dados = new DadosCadastroAbastecimento(
                1L, 1L, LocalDate.now(), 1500, BigDecimal.valueOf(7), BigDecimal.valueOf(-1), 1L);
        var motoboy = mock(Motoboy.class);
        var posto = mock(Posto.class);
        var usuario = mock(Usuario.class);
        assertThrows(RegraDeNegocioException.class, () -> new Abastecimento(dados, motoboy, usuario, posto));
    }

    @Test
    void calcularValorTotalCorretamente() {
        var abastecimento = new Abastecimento();
        abastecimento.setLitros(BigDecimal.valueOf(2.78));
        abastecimento.setValorLitro(BigDecimal.valueOf(2.78));
        var resultado= abastecimento.calculoCombustivel().setScale(2, RoundingMode.HALF_UP);
        assertEquals(BigDecimal.valueOf(7.73), resultado);
    }


    @Test
    void deveLancarErroQuandoKmMomentoForMenorQueKmEntrada(){
        var dados = new DadosCadastroAbastecimento(
                1L, 1L, LocalDate.now(), 1500, BigDecimal.valueOf(7), BigDecimal.valueOf(3), 1L);
        var motoboy = mock(Motoboy.class);
        var posto = mock(Posto.class);
        var usuario = mock(Usuario.class);
        var registro= new RegistroKm();
        registro.setKmEntrada(1600);
        var abastecimento= new Abastecimento(dados, motoboy, usuario, posto);
        assertThrows(RegraDeNegocioException.class,()-> abastecimento.validarKmAbastecimento(registro));
    }

    @Test
    void devePermitirQuandoKmEstiverEntreKmDeEntradaEFim(){
        var dados = new DadosCadastroAbastecimento(
                1L, 1L, LocalDate.now(), 1500, BigDecimal.valueOf(7), BigDecimal.valueOf(5), 1L);
        var motoboy = mock(Motoboy.class);
        var posto = mock(Posto.class);
        var usuario = mock(Usuario.class);
        var registro= new RegistroKm();
        registro.setKmEntrada(1400);
        registro.setKmFim(1500);
        var resultado= new Abastecimento(dados, motoboy, usuario, posto);
        assertNotNull(resultado);
    }
}

