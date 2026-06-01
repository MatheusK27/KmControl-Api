package com.matheus.service;

import com.matheus.entidades.dtoEntrada.DadosCadastroRegistroKm;

import com.matheus.entidades.entidades.*;
import com.matheus.entidades.repositorio.*;
import com.matheus.infra.RegraDeNegocioException;
import com.matheus.servico.RegistroKmServico;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegistroKmServicoTest {


    @Mock
    private MotoboyRepositorio motoboyRepositorio;


    @Mock
    private RegistroKmRepositorio registroKmRepositorio;


    @InjectMocks
    private RegistroKmServico registroKmServico;

    @Test
    void naoDeveCadastrarRegistroKmQuandoNaoExistirMotoboy() {
        var dados = new DadosCadastroRegistroKm(
                1L, LocalDate.now(), 100, 110, 110, 120);
        when(motoboyRepositorio.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RegraDeNegocioException.class, () -> registroKmServico.cadastrarKmDiario(dados));
    }

    @Test
    void naoDeveCadastrarRegistroKmQuandoMotoobyEstiverInativo() {
        var dados = new DadosCadastroRegistroKm(
                1L, LocalDate.now(), 100, 110, 110, 120);
        var motoboy = mock(Motoboy.class);
        when(motoboyRepositorio.findById(1L)).thenReturn(Optional.of(motoboy));
        doThrow(new RegraDeNegocioException("Motoboy inativo")).when(motoboy).validarAtivo();
        assertThrows(RegraDeNegocioException.class, () -> registroKmServico.cadastrarKmDiario(dados));
        verify(registroKmRepositorio, never()).save(any());

    }

    @Test
    void naoDeveCadastrarRegistroKmQuandoUltimoKmEstiverEmAberto() {
        var dados = new DadosCadastroRegistroKm(
                1L, LocalDate.now(), 100, 110, 110, 120);
        var motoboy = mock(Motoboy.class);
        when(motoboyRepositorio.findById(1L)).thenReturn(Optional.of(motoboy));
        when(registroKmRepositorio.motoboyPossuiKmAbertoForaDaDataAtual(dados.motoboyId(), dados.data())).thenReturn(true);
        var erro = assertThrows(RegraDeNegocioException.class, () -> registroKmServico.cadastrarKmDiario(dados));
        assertEquals("Existe um km em aberto desse motoboy", erro.getMessage());
        verify(registroKmRepositorio, never()).save(any());
    }

    @Test
    void NaoDevecadastrarQuandoJaExisteRegistroNaMesmaData() {
        var dados = new DadosCadastroRegistroKm(
                1L, LocalDate.now(), 100, 110, 110, 120);
        var motoboy = mock(Motoboy.class);
        when(motoboyRepositorio.findById(1L)).thenReturn(Optional.of(motoboy));
        when(registroKmRepositorio.existsByMotoboyIdAndData(dados.motoboyId(), dados.data())).thenReturn(true);
        var erro = assertThrows(RegraDeNegocioException.class, () -> registroKmServico.cadastrarKmDiario(dados));
        assertEquals("Motoboy já possui registro nesta data", erro.getMessage());
        verify(registroKmRepositorio, never()).save(any());
    }

    @Test
    void NaoDeveCadastrarQuandoKmDeEntradaForMenorQueUltimoKMFinal() {
        var dados = new DadosCadastroRegistroKm(
                1L, LocalDate.now(), 100, 110, 110, 120);
        var motoboy = mock(Motoboy.class);
        var registro = mock(RegistroKm.class);
        when(motoboyRepositorio.findById(1L)).thenReturn(Optional.of(motoboy));
        when(registroKmRepositorio.findTopByMotoboyIdOrderByKmFimDesc(dados.motoboyId())).thenReturn(Optional.of(registro));
        when(registro.getKmFim()).thenReturn(121);
        var erro = assertThrows(RegraDeNegocioException.class, () -> registroKmServico.cadastrarKmDiario(dados));
        assertEquals("Km de inicio não pode ser menor que Km anterior", erro.getMessage());
        verify(registroKmRepositorio, never()).save(any());

    }

    @Test
    void DeveCadastrarQuandoNaoExisteUltimoKm() {
        var dados = new DadosCadastroRegistroKm(
                1L, LocalDate.now(), 100, 110, 110, 120);
        var motoboy = mock(Motoboy.class);
        when(motoboyRepositorio.findById(1L)).thenReturn(Optional.of(motoboy));
        when(registroKmRepositorio.findTopByMotoboyIdOrderByKmFimDesc(dados.motoboyId())).thenReturn(Optional.empty());
        var resultado = registroKmServico.cadastrarKmDiario(dados);
        assertNotNull(resultado);
    }

    @Test
    void DeveCadastrarQuandoKmDeEntradaForIgualKmFim() {
        var dados = new DadosCadastroRegistroKm(
                1L, LocalDate.now(), 100, 100, 100, 100);
        var motoboy = mock(Motoboy.class);
        when(motoboyRepositorio.findById(1L)).thenReturn(Optional.of(motoboy));
        var resultado= registroKmServico.cadastrarKmDiario(dados);
        assertNotNull(resultado);
    }

    @Test
    void DeveCadastrarKmComSucesso(){
        var dados = new DadosCadastroRegistroKm(
                1L, LocalDate.now(), 100, 100, 100, 100);
        var motoboy = mock(Motoboy.class);
        var registro = mock(RegistroKm.class);
        when(motoboyRepositorio.findById(1L)).thenReturn(Optional.of(motoboy));
        when(registroKmRepositorio.existsByMotoboyIdAndData(dados.motoboyId(), dados.data())).thenReturn(false);
        when(registroKmRepositorio.motoboyPossuiKmAbertoForaDaDataAtual(dados.motoboyId(), dados.data())).thenReturn(false);
        when(registroKmRepositorio.findTopByMotoboyIdOrderByKmFimDesc(dados.motoboyId())).thenReturn(Optional.of(registro));
        var resultado= registroKmServico.cadastrarKmDiario(dados);
        assertNotNull(resultado);

    }
}
