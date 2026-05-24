package com.matheus.dominio.service;


import com.matheus.dominio.dtoEntrada.DadosCadastroPosto;
import com.matheus.dominio.dtoSaida.DadosDetalhamentoAbastecimento;
import com.matheus.dominio.dtoSaida.DadosDetalhamentoPosto;
import com.matheus.dominio.dtoSaida.DadosDetalhamentoUsuario;
import com.matheus.dominio.entidades.Posto;
import com.matheus.dominio.repositorio.AbastecimentoRepositorio;
import com.matheus.dominio.repositorio.PostoRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostoSevice {


    private final PostoRepositorio repositorio;

    private final AbastecimentoRepositorio abastecimentoRepositorio;

    public DadosDetalhamentoPosto cadastrarPosto(DadosCadastroPosto dados) {
        var posto = new Posto(dados);
        repositorio.save(posto);
        return new DadosDetalhamentoPosto(posto);
    }

    public void desativarPosto(Long id){
        var posto= repositorio.findById(id).orElseThrow(()-> new RuntimeException("Posto não encontrado"));
        posto.desativarPosto(id);
        repositorio.save(posto);
    }

    public DadosDetalhamentoPosto detalharPosto(Long id){
        var posto= repositorio.findById(id).orElseThrow(()-> new RuntimeException("Posto não cadastrado"));
        return new DadosDetalhamentoPosto(posto);
    }




}
