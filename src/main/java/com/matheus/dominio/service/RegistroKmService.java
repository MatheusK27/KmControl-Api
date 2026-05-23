package com.matheus.dominio.service;

import com.matheus.dominio.dtoEntrada.DadosAtualizarRegistroKm;
import com.matheus.dominio.dtoEntrada.DadosCadastroRegistroKm;
import com.matheus.dominio.dtoSaida.DadosDetalhamentoRegistroKm;
import com.matheus.dominio.entidades.RegistroKm;
import com.matheus.dominio.repositorio.MotoboyRepositorio;
import com.matheus.dominio.repositorio.RegistroKmRepositorio;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistroKmService {

    @Autowired
    private RegistroKmRepositorio repositorio;
    @Autowired
    private MotoboyRepositorio motoboyRepositorio;

    public DadosDetalhamentoRegistroKm cadastrarKmDiario(DadosCadastroRegistroKm dados){
        if (dados.kmRetornoAlmoco()<dados.kmSaidaAlmoco() || dados.kmFim()< dados.kmInicio()){
            throw  new ValidationException("Km retorno almoco ou saida incorretos");
        }

        var ultimoKm= repositorio.findTopByMotoboyIdOrderByKmFimDesc(dados.motoboyId());
        if(ultimoKm.isPresent()){

            var kmFinal=  ultimoKm.get().getKmFim();

            if(dados.kmInicio() < kmFinal){
                throw  new ValidationException("Km de inicio não pode ser menor que Km anterior");
            }
        }
        var motoboy= motoboyRepositorio.findById(dados.motoboyId()).orElseThrow(() -> new RuntimeException("Motoboy não encontrado"));
        if(!motoboy.getAtivo()){
            throw new ValidationException("Não é permitido cadastro de km com motoboy inatico");
        }
        var registroKm= new RegistroKm(dados, motoboy);
        repositorio.save(registroKm);
        return new DadosDetalhamentoRegistroKm(registroKm);
    }

    public DadosDetalhamentoRegistroKm atualizarKm (DadosAtualizarRegistroKm dados){
        var km= repositorio.findById(dados.id()).orElseThrow(() -> new RuntimeException("Registro KM não encontrado"));
        km.atualizarKm(dados);
        repositorio.save(km);
        return new DadosDetalhamentoRegistroKm(km);
    }

}
