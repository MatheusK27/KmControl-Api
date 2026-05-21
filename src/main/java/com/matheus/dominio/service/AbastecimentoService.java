package com.matheus.dominio.service;


import com.matheus.dominio.dtoEntrada.DadosAtualizarAbastecimento;
import com.matheus.dominio.dtoEntrada.DadosCadastroAbastecimento;
import com.matheus.dominio.dtoSaida.DadosDetalhamentoAbastecimento;

import com.matheus.dominio.entidades.Abastecimento;
import com.matheus.dominio.repositorio.AbastecimentoRepositorio;
import com.matheus.dominio.repositorio.MotoboyRepositorio;
import com.matheus.dominio.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AbastecimentoService {

    @Autowired
    private AbastecimentoRepositorio repositorio;

    @Autowired
    private MotoboyRepositorio motoboyRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;


    public DadosDetalhamentoAbastecimento cadastroAbastecimento(DadosCadastroAbastecimento dados) {
        if(dados.litros().compareTo(BigDecimal.ZERO)>8 ){
        throw new RuntimeException("Limite de abastecimento excedido, permitido somente 8");
        }
        var motoboy= motoboyRepositorio.findById(dados.motoboyId()).orElseThrow(() -> new RuntimeException("Motoboy não encontrado"));
        var usuario= usuarioRepositorio.findById(dados.usuarioId()).orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
        var abastecimento= new Abastecimento(dados, motoboy,usuario);
        var total = calculoCombustivel(abastecimento);
        abastecimento.setValorTotal(BigDecimal.valueOf(total));
        repositorio.save(abastecimento);
        return new DadosDetalhamentoAbastecimento(abastecimento);
    }

    public DadosDetalhamentoAbastecimento atualizarAbastecimento (DadosAtualizarAbastecimento dados,Long id){
        var dadosAtualizados= repositorio.findById(id).orElseThrow(()-> new RuntimeException("ID abastecimento não encontrado"));
        dadosAtualizados.atualizarAbastecimento(dados);
        repositorio.save(dadosAtualizados);
        return new DadosDetalhamentoAbastecimento(dadosAtualizados);
    }





    private double calculoCombustivel(Abastecimento abastecimento) {
        BigDecimal valorTotalCombustivel = abastecimento.getValorLitro().multiply(abastecimento.getLitros());
        return valorTotalCombustivel.doubleValue();
    }



}
