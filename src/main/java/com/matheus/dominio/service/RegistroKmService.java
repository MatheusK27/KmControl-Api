package com.matheus.dominio.service;

import com.matheus.dominio.dto.DadosCadastroRegistroKm;
import com.matheus.dominio.dto.DadosDetalhamentoAbastecimento;
import com.matheus.dominio.dto.DadosDetalhamentoRegistroKm;
import com.matheus.dominio.entidades.Motoboy;
import com.matheus.dominio.entidades.RegistroKm;
import com.matheus.dominio.repositorio.MotoboyRepositorio;
import com.matheus.dominio.repositorio.RegistroKmRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistroKmService {

    @Autowired
    private RegistroKmRepositorio repositorio;
    @Autowired
    private MotoboyRepositorio motoboyRepositorio;

    public DadosDetalhamentoRegistroKm cadastrarKmDiario(DadosCadastroRegistroKm dados){
        if (dados.kmRetornoAlmoco()<dados.kmSaidaAlmoco() && dados.kmFim()< dados.kmInicio()){
            throw  new RuntimeException("Km retorno almoco ou saida incorreta");
        }
        var motoboy= motoboyRepositorio.findById(dados.motoboyId()).orElseThrow(() -> new RuntimeException("Motoboy não encontrado"));
        var km= new RegistroKm(dados, motoboy);
        repositorio.save(km);
        return new DadosDetalhamentoRegistroKm(km);
    }

}
