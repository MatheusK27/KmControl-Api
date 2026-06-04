package com.matheus.service;


import com.matheus.servico.AbastecimentoService;
import com.matheus.entidades.dtoEntrada.DadosCadastroAbastecimento;

import com.matheus.entidades.entidades.*;
import com.matheus.entidades.repositorio.*;
import com.matheus.infra.RegraDeNegocioException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class AbastecimentoServiceTest {

    @Mock
    private AbastecimentoRepositorio repositorio;

    @Mock
    private MotoboyRepositorio motoboyRepositorio;

    @Mock
    private UsuarioRepositorio usuarioRepositorio;

    @Mock
    private PostoRepositorio postoRepositorio;

    @Mock
    private RegistroKmRepositorio registroKmRepositorio;

    @InjectMocks
    private AbastecimentoService abastecimentoService;


    @Test
    void naoDeveCadastrarAbastecimentoQuandoMotoboyNaoExiste() {
        var dados = new DadosCadastroAbastecimento(
                1L, 1L, LocalDate.now(), 1500, BigDecimal.TEN, BigDecimal.ONE, 1L);
        when(motoboyRepositorio.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RegraDeNegocioException.class, () -> {
            abastecimentoService.cadastroAbastecimento(dados);

        });

    }

    @Test
    void naoDeveCadastrarQuandoPostoNaoExiste() {
        var dados = new DadosCadastroAbastecimento(1L, 1L, LocalDate.now(), 1500, BigDecimal.TEN, BigDecimal.ONE, 1L);
        var motoboy = mock(Motoboy.class);
        var usuario = mock(Usuario.class);
        when(motoboyRepositorio.findById(1L)).thenReturn(Optional.of(motoboy));
        when(usuarioRepositorio.findById(1L)).thenReturn(Optional.of(usuario));
        when(postoRepositorio.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RegraDeNegocioException.class, () -> {
            abastecimentoService.cadastroAbastecimento(dados);
        });

    }

    @Test
    void naoDeveCadastrarQuandoUsuarioNaoExiste() {
        var dados = new DadosCadastroAbastecimento(1L, 1L, LocalDate.now(), 1500, BigDecimal.TEN, BigDecimal.ONE, 1L);
        var motoboy = mock(Motoboy.class);
        when(motoboyRepositorio.findById(1L)).thenReturn(Optional.of(motoboy));
        when(usuarioRepositorio.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RegraDeNegocioException.class, () -> {
            abastecimentoService.cadastroAbastecimento(dados);
        });
    }

    @Test
    void deveCadastrarQuandoTudoEstiverValido() {
        var dados = new DadosCadastroAbastecimento(1L, 1L, LocalDate.now(), 1500, BigDecimal.valueOf(7), BigDecimal.ONE, 1L);
        var motoboy = mock(Motoboy.class);
        var usuario = mock(Usuario.class);
        var posto = mock(Posto.class);
        var registro = mock(RegistroKm.class);
        when(motoboyRepositorio.findById(1L)).thenReturn(Optional.of(motoboy));
        when(usuarioRepositorio.findById(1L)).thenReturn(Optional.of(usuario));
        when(postoRepositorio.findById(1L)).thenReturn(Optional.of(posto));
        when(registroKmRepositorio.findByMotoboyIdAndDataAndKmFimIsNull(dados.motoboyId(), dados.data())).thenReturn(Optional.of(registro));
        when(registro.getKmFim()).thenReturn(2000);
        var resultado = abastecimentoService.cadastroAbastecimento(dados);
        verify(motoboy).validarAtivo();
        verify(posto).validarAtivo();
        verify(repositorio).save(any(Abastecimento.class));
        assertNotNull(resultado);

    }

    @Test
    void naoDeveCadastrarQuandoNaoExisteKmEmAberto() {
        var dados = new DadosCadastroAbastecimento(1L, 1L, LocalDate.now(), 1500, BigDecimal.valueOf(7), BigDecimal.ONE, 1L);
        var motoboy = mock(Motoboy.class);
        var usuario = mock(Usuario.class);
        var posto = mock(Posto.class);
        when(motoboyRepositorio.findById(1L)).thenReturn(Optional.of(motoboy));
        when(usuarioRepositorio.findById(1L)).thenReturn(Optional.of(usuario));
        when(postoRepositorio.findById(1L)).thenReturn(Optional.of(posto));
        when(registroKmRepositorio.findByMotoboyIdAndDataAndKmFimIsNull(dados.motoboyId(), dados.data())).thenReturn(Optional.empty());
        var error = assertThrows(RegraDeNegocioException.class, () -> {
            abastecimentoService.cadastroAbastecimento(dados);
        });
        assertEquals("Não existe KM aberto para esse motoboy", error.getMessage());
        verify(repositorio, never()).save(any());
    }

    @Test
    void naoDeveSalvarQuandoOMotoboyEstiverInativo() {
        var dados = new DadosCadastroAbastecimento(1L, 1L, LocalDate.now(), 1500, BigDecimal.valueOf(7), BigDecimal.ONE, 1L);
        var motoboy = mock(Motoboy.class);
        var usuario = mock(Usuario.class);
        var posto = mock(Posto.class);
        var registro = mock(RegistroKm.class);
        when(motoboyRepositorio.findById(1L)).thenReturn(Optional.of(motoboy));
        when(usuarioRepositorio.findById(1L)).thenReturn(Optional.of(usuario));
        when(postoRepositorio.findById(1L)).thenReturn(Optional.of(posto));
        when(registroKmRepositorio.findByMotoboyIdAndDataAndKmFimIsNull(dados.motoboyId(), dados.data())).thenReturn(Optional.of(registro));
        doThrow(new RegraDeNegocioException("Motoboy inativo")).when(motoboy).validarAtivo();
        assertThrows(
                RegraDeNegocioException.class,
                () -> abastecimentoService.cadastroAbastecimento(dados)
        );
        verify(repositorio, never()).save(any());

    }

    @Test
    void naoDeveSalvarQuandoOPostoEstiverInativo() {
        var dados = new DadosCadastroAbastecimento(1L, 1L, LocalDate.now(), 1500, BigDecimal.valueOf(7), BigDecimal.ONE, 1L);
        var motoboy = mock(Motoboy.class);
        var usuario = mock(Usuario.class);
        var posto = mock(Posto.class);
        var registro = mock(RegistroKm.class);
        when(motoboyRepositorio.findById(1L)).thenReturn(Optional.of(motoboy));
        when(usuarioRepositorio.findById(1L)).thenReturn(Optional.of(usuario));
        when(postoRepositorio.findById(1L)).thenReturn(Optional.of(posto));
        when(registroKmRepositorio.findByMotoboyIdAndDataAndKmFimIsNull(dados.motoboyId(), dados.data())).thenReturn(Optional.of(registro));
        doThrow(new RegraDeNegocioException("Posto inativo")).when(posto).validarAtivo();
        assertThrows(RegraDeNegocioException.class, () -> abastecimentoService.cadastroAbastecimento(dados));
        verify(repositorio, never()).save(any());

    }

    @Test
    void naoDeveCadastrarQuandoKmAbastecimentoForMaiorQueKmFinal() {
        var dados = new DadosCadastroAbastecimento(1L, 1L, LocalDate.now(), 1500, BigDecimal.valueOf(7), BigDecimal.ONE, 1L);
        var motoboy = mock(Motoboy.class);
        var usuario = mock(Usuario.class);
        var posto = mock(Posto.class);
        var registro = mock(RegistroKm.class);
        when(motoboyRepositorio.findById(1L)).thenReturn(Optional.of(motoboy));
        when(usuarioRepositorio.findById(1L)).thenReturn(Optional.of(usuario));
        when(postoRepositorio.findById(1L)).thenReturn(Optional.of(posto));
        when(registroKmRepositorio.findByMotoboyIdAndDataAndKmFimIsNull(dados.motoboyId(), dados.data())).thenReturn(Optional.of(registro));
        when(registro.getKmEntrada()).thenReturn(1000);
        when(registro.getKmFim()).thenReturn(1400);
        var erro = assertThrows(RegraDeNegocioException.class, () -> abastecimentoService.cadastroAbastecimento(dados));
        assertEquals("Km de abastecimento não pode ser maior que km final", erro.getMessage());
        verify(repositorio, never()).save(any());

    }

    @Test
    void naoDeveCadastrarQuandoKmMomentoForMenorQueUltimoAbastecimento() {
        var dados = new DadosCadastroAbastecimento(1L, 1L, LocalDate.now(), 1500, BigDecimal.valueOf(7), BigDecimal.ONE, 1L);
        var motoboy = mock(Motoboy.class);
        var usuario = mock(Usuario.class);
        var posto = mock(Posto.class);
        var registro = mock(RegistroKm.class);
        var ultimoAbastecimento = mock(Abastecimento.class);
        when(motoboyRepositorio.findById(1L)).thenReturn(Optional.of(motoboy));
        when(usuarioRepositorio.findById(1L)).thenReturn(Optional.of(usuario));
        when(postoRepositorio.findById(1L)).thenReturn(Optional.of(posto));
        when(registroKmRepositorio.findByMotoboyIdAndDataAndKmFimIsNull(dados.motoboyId(), dados.data())).thenReturn(Optional.of(registro));
        when(repositorio.findTopByMotoboyIdOrderByKmMomentoDesc(dados.motoboyId())).thenReturn(Optional.of(ultimoAbastecimento));
        when(repositorio.findTopByMotoboyIdOrderByKmMomentoDesc(dados.motoboyId())).thenReturn(Optional.of(ultimoAbastecimento));
        when(ultimoAbastecimento.getKmMomento()).thenReturn(1600);
        var erro = assertThrows(RegraDeNegocioException.class, () -> abastecimentoService.cadastroAbastecimento(dados));
        assertEquals("KM não pode ser menor que o último abastecimento registrado", erro.getMessage());
        verify(repositorio, never()).save(any());
    }

    @Test
    void naoDeveCadastrarQuandoMotoboyAindaTiverAutonimoDeCombustivel() {
        var dados = new DadosCadastroAbastecimento(1L, 1L, LocalDate.now(), 1200, BigDecimal.valueOf(7), BigDecimal.ONE, 1L);
        var motoboy = mock(Motoboy.class);
        var usuario = mock(Usuario.class);
        var posto = mock(Posto.class);
        var ultimoAbastecimento = mock(Abastecimento.class);
        var registro = mock(RegistroKm.class);
        when(motoboyRepositorio.findById(1L)).thenReturn(Optional.of(motoboy));
        when(usuarioRepositorio.findById(1L)).thenReturn(Optional.of(usuario));
        when(postoRepositorio.findById(1L)).thenReturn(Optional.of(posto));
        when(registroKmRepositorio.findByMotoboyIdAndDataAndKmFimIsNull(dados.motoboyId(), dados.data())).thenReturn(Optional.of(registro));
        when(repositorio.findTopByMotoboyIdOrderByKmMomentoDesc(dados.motoboyId())).thenReturn(Optional.of(ultimoAbastecimento));
        when(ultimoAbastecimento.getKmMomento()).thenReturn(1000);
        when(ultimoAbastecimento.getLitros()).thenReturn(BigDecimal.TEN);
        var erro = assertThrows(RegraDeNegocioException.class, () -> {
            abastecimentoService.cadastroAbastecimento(dados);
        });
        assertEquals(
                "Abastecimento só permitido próximo ao fim da autonomia",
                erro.getMessage());
        verify(repositorio, never()).save(any());

    }

    @Test
    void deveCadastrarQuandoKmMomentoEstiverProximoDoFimDaAutonomia() {
        var dados = new DadosCadastroAbastecimento(1L, 1L, LocalDate.now(), 1000, BigDecimal.valueOf(7), BigDecimal.ONE, 1L);
        var motoboy = mock(Motoboy.class);
        var usuario = mock(Usuario.class);
        var posto = mock(Posto.class);
        var ultimoAbastecimento = mock(Abastecimento.class);
        var registro = mock(RegistroKm.class);
        when(motoboyRepositorio.findById(1L)).thenReturn(Optional.of(motoboy));
        when(usuarioRepositorio.findById(1L)).thenReturn(Optional.of(usuario));
        when(postoRepositorio.findById(1L)).thenReturn(Optional.of(posto));
        when(registroKmRepositorio.findByMotoboyIdAndDataAndKmFimIsNull(dados.motoboyId(), dados.data())).thenReturn(Optional.of(registro));
        when(repositorio.findTopByMotoboyIdOrderByKmMomentoDesc(dados.motoboyId())).thenReturn(Optional.of(ultimoAbastecimento));
        when(ultimoAbastecimento.getKmMomento()).thenReturn(200);
        when(ultimoAbastecimento.getLitros()).thenReturn(BigDecimal.ONE);
        when(registro.getKmEntrada()).thenReturn(0);
        when(registro.getKmFim()).thenReturn(2000);

        var resultado = abastecimentoService.cadastroAbastecimento(dados);
        verify(repositorio).save(any(Abastecimento.class));
        assertNotNull(resultado);
    }

    @Test
    void naoDeveCadastrarQuandoKmAbastecimentoForMenorQueKmEntrada() {
        var dados = new DadosCadastroAbastecimento(1L, 1L, LocalDate.now(), 1000, BigDecimal.valueOf(7), BigDecimal.ONE, 1L);
        var motoboy = mock(Motoboy.class);
        var usuario = mock(Usuario.class);
        var posto = mock(Posto.class);
        var registro = mock(RegistroKm.class);
        when(motoboyRepositorio.findById(1L)).thenReturn(Optional.of(motoboy));
        when(usuarioRepositorio.findById(1L)).thenReturn(Optional.of(usuario));
        when(postoRepositorio.findById(1L)).thenReturn(Optional.of(posto));
        when(registroKmRepositorio.findByMotoboyIdAndDataAndKmFimIsNull(dados.motoboyId(), dados.data())).thenReturn(Optional.of(registro));
        when(registro.getKmEntrada()).thenReturn(1100);
        var erro = assertThrows(RegraDeNegocioException.class, () -> {
            abastecimentoService.cadastroAbastecimento(dados);
        });
        assertEquals("Km de abastecimento não pode ser menor que km de entrada", erro.getMessage());
        verify(repositorio, never()).save(any(Abastecimento.class));

    }

    @Test
    void deveCadastrarQuandoNaoExisteAbastecimentoAnterior() {
        var dados = new DadosCadastroAbastecimento(1L, 1L, LocalDate.now(), 1200, BigDecimal.valueOf(7), BigDecimal.ONE, 1L);
        var motoboy = mock(Motoboy.class);
        var usuario = mock(Usuario.class);
        var posto = mock(Posto.class);
        var registro = mock(RegistroKm.class);
        when(motoboyRepositorio.findById(1L)).thenReturn(Optional.of(motoboy));
        when(usuarioRepositorio.findById(1L)).thenReturn(Optional.of(usuario));
        when(postoRepositorio.findById(1L)).thenReturn(Optional.of(posto));
        when(registroKmRepositorio.findByMotoboyIdAndDataAndKmFimIsNull(dados.motoboyId(), dados.data())).thenReturn(Optional.of(registro));
        when(repositorio.findTopByMotoboyIdOrderByKmMomentoDesc(dados.motoboyId())).thenReturn(Optional.empty());
        when(registro.getKmEntrada()).thenReturn(1000);
        when(registro.getKmFim()).thenReturn(1300);
        var resultado = abastecimentoService.cadastroAbastecimento(dados);
        verify(repositorio).save(any(Abastecimento.class));
        assertNotNull(resultado);
    }
}
