package com.matheus.service;


import com.matheus.dominio.dto.DadosCadastroMotoboy;
import com.matheus.dominio.dto.DadosDetalhamentoMotoboy;
import com.matheus.dominio.entidades.Motoboy;
import com.matheus.repositorio.MotoboyRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MotoboyServico {

    @Autowired
    private MotoboyRepositorio repositorio;

    public DadosDetalhamentoMotoboy cadastrarMotoboy(DadosCadastroMotoboy dados) {
        var motoboy = new Motoboy(dados);
        repositorio.save(motoboy);
        return new DadosDetalhamentoMotoboy(motoboy);
    }



}
