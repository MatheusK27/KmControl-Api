package com.matheus.dominio.service;


import com.matheus.dominio.dto.DadosCadastroAbastecimento;
import com.matheus.dominio.dto.DadosDetalhamentoAbastecimento;
import com.matheus.dominio.entidades.Abastecimento;
import com.matheus.dominio.repositorio.AbastecimentoRepositorio;
import com.matheus.dominio.repositorio.MotoboyRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AbastecimentoService {

    @Autowired
    private AbastecimentoRepositorio repositorio;

    @Autowired
    private MotoboyRepositorio motoboyRepositorio;


    public DadosDetalhamentoAbastecimento fornecerAbastecimento(DadosCadastroAbastecimento dados) {
        var motoboy= motoboyRepositorio.findById(dados.motoboyId()).orElseThrow(() -> new RuntimeException("Motoboy não encontrado"));
        var abastecimento= new Abastecimento(dados, motoboy);
        return new DadosDetalhamentoAbastecimento(abastecimento);
    }
}
