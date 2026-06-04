package com.matheus.service;


import com.matheus.entidades.dtoEntrada.DadosCadastroPosto;
import com.matheus.entidades.repositorio.PostoRepositorio;
import com.matheus.servico.PostoServico;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostoServiceTest {

    @Mock
    private PostoRepositorio repositorio;

    @InjectMocks
    private PostoServico service;

    @Test
    void naoDeveCadastrarQuandoCnpjJaExiste() {
        var dados=  new DadosCadastroPosto("Nome", "123456789","99999999","posto@","cidade");
        when(repositorio.existsByCnpj(dados.cnpj())).thenReturn(true);
        var erro = assertThrows(RegraDeNegocioException.class, () -> {service.cadastrarPosto(dados);});
        assertEquals("Já existe cadastro com esse cnpj", erro.getMessage());
        verify(repositorio, never()).save(any());
    }

    @Test
    void naoDeveCadastrarPostoQuandoEmailJaExistir() {
        var dados=  new DadosCadastroPosto("Nome", "123456789","99999999","posto@","cidade");
        when(repositorio.existsByEmail(dados.cnpj())).thenReturn(true);
        var erro= assertThrows(RegraDeNegocioException.class, () -> {service.cadastrarPosto(dados);});
        assertEquals("Já existe cadastro com esse email", erro.getMessage());
        verify(repositorio, never()).save(any());
    }

    @Test
    void deveCadastrarQuandoTudoTiverValido(){
        var dados=  new DadosCadastroPosto("Nome", "123456789","99999999","posto@","cidade");
        when(repositorio.existsByCnpj(dados.cnpj())).thenReturn(false);
        when(repositorio.existsByEmail(dados.email())).thenReturn(false);
        var resultado=  service.cadastrarPosto(dados);
        assertNotNull(resultado);
    }

    }
