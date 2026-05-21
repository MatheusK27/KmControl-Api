package com.matheus.dominio.service;


import com.matheus.dominio.dtoEntrada.DadosCadastroMotoboy;
import com.matheus.dominio.dtoSaida.DadosDetalhamentoMotoboy;
import com.matheus.dominio.entidades.Motoboy;
import com.matheus.dominio.repositorio.MotoboyRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MotoboyServico {

    @Autowired
    private MotoboyRepositorio repositorio;

    public  DadosDetalhamentoMotoboy cadastrarMotoboy(DadosCadastroMotoboy dados) {
    if(repositorio.existsByPlaca(dados.placa())){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"JÁ EXISTE UM MOTOBOY COM ESSA PLACA");
    }
        var motoboy = new Motoboy(dados);
        repositorio.save(motoboy);
        return new DadosDetalhamentoMotoboy(motoboy);
    }


    public List<DadosDetalhamentoMotoboy> listarMotoboy() {
        var lista  = repositorio.findByAtivoTrue().stream().map(DadosDetalhamentoMotoboy::new).toList();
        return lista;

    }

    public DadosDetalhamentoMotoboy buscarPorId(Long id) {
        var motoboy = repositorio.findById(id).orElseThrow(()->new RuntimeException("Motoboy não encontrado"));
        return new DadosDetalhamentoMotoboy(motoboy);
    }

    public void desativarMotoboy(Long id) {
        var motoboy=repositorio.getReferenceById(id);
        motoboy.setAtivo(false);
        repositorio.save(motoboy);


    }

}
