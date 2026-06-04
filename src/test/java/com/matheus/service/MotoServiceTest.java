package com.matheus.service;


import com.matheus.entidades.dtoEntrada.DadosCadastroMotoboy;

import com.matheus.entidades.repositorio.MotoboyRepositorio;
import com.matheus.servico.MotoboyServico;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.matheus.entidades.entidades.*;
import com.matheus.entidades.repositorio.*;
import com.matheus.infra.RegraDeNegocioException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
public class MotoServiceTest {

    @Mock
    private RegistroKmRepositorio registroKmRepositorio;
    @Mock
    private MotoboyRepositorio motoboyRepositorio;

    @InjectMocks
    private MotoboyServico motoboyServico;

    @Test
    void naoDeveCadastrarCadastroMotoboyComMesmaPlaca() {
        var dados= new DadosCadastroMotoboy("Matheus", "123456", "9999999","aaa123");
        when(motoboyRepositorio.existsByPlaca(dados.placa())).thenReturn(true);
        var erro= assertThrows(RegraDeNegocioException.class,()->{motoboyServico.cadastrarMotoboy(dados);});
        assertEquals("Placa já cadastrada",erro.getMessage());
        verify(motoboyRepositorio,never()).save(any());
    }

    @Test
    void naoDeveCadastrarCadastroMotoboyComMesmaCnh(){
        var dados= new DadosCadastroMotoboy("Matheus", "123456", "9999999","aaa123");
        when(motoboyRepositorio.existsByCnh(dados.cnh())).thenReturn(true);
        var erro = assertThrows(RegraDeNegocioException.class,()->{motoboyServico.cadastrarMotoboy(dados);});
        assertEquals("Já existe motoboy cadastrado com essa CNH", erro.getMessage());
        verify(motoboyRepositorio,never()).save(any());
    }

    @Test
    void naoDeveDesativarQuandoMotoboyTiverKmEmAberto(){
        Long id=1L;
        when(registroKmRepositorio.existsByMotoboyIdAndKmFimIsNull(id)).thenReturn(true);
        var erro = assertThrows(RegraDeNegocioException.class,()->{motoboyServico.desativarMotoboy(id);});
        assertEquals("Motoboy possui registro de KM em aberto",erro.getMessage());
        verify(motoboyRepositorio,never()).save(any());

    }

    @Test
    void deveCadastrarQuandoTudoEstiverValido(){
        var dados= new DadosCadastroMotoboy("Matheus", "123456", "9999999","aaa123");
        when(motoboyRepositorio.existsByPlaca(any())).thenReturn(false);
        when(motoboyRepositorio.existsByCnh(any())).thenReturn(false);
        var resultado= motoboyServico.cadastrarMotoboy(dados);
        assertNotNull(resultado);

    }

}
