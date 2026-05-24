package com.matheus.dominio.service;


import com.matheus.dominio.dtoEntrada.DadosAtualizarMotoboy;
import com.matheus.dominio.dtoEntrada.DadosCadastroMotoboy;
import com.matheus.dominio.dtoSaida.DadosDetalhamentoMotoboy;
import com.matheus.dominio.entidades.Motoboy;
import com.matheus.dominio.repositorio.MotoboyRepositorio;
import com.matheus.dominio.repositorio.RegistroKmRepositorio;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class    MotoboyServico {

    private final MotoboyRepositorio repositorio;

    private final RegistroKmRepositorio registroKmRepositorio;


    public  DadosDetalhamentoMotoboy cadastrarMotoboy(DadosCadastroMotoboy dados) {

    if(repositorio.existsByPlaca(dados.placa())){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"JÁ EXISTE UM MOTOBOY COM ESSA PLACA");
    }

        if(repositorio.existsByCnh(dados.cnh())){
        throw  new ValidationException("Já existe motoboy cadastrado com esse CNH");
    }
        var motoboy = new Motoboy(dados);
        repositorio.save(motoboy);
        return new DadosDetalhamentoMotoboy(motoboy);
    }


    public Page<DadosDetalhamentoMotoboy> listarMotoboy(Pageable pagina ) {
        return repositorio.findByAtivoTrue(pagina).map(DadosDetalhamentoMotoboy::new);
    }

    public DadosDetalhamentoMotoboy buscarPorId(Long id) {
        var motoboy = repositorio.findById(id).orElseThrow(()->new RuntimeException("Motoboy não encontrado"));
        return new DadosDetalhamentoMotoboy(motoboy);
    }
    public DadosDetalhamentoMotoboy buscarMotoboyPorPlaca(String placa) {
        var motoboy= repositorio.findByPlaca(placa).orElseThrow(()-> new RuntimeException("Motoboy não encontrado pela placa"));
        return new DadosDetalhamentoMotoboy(motoboy);
    }

    public DadosDetalhamentoMotoboy atualizarMotoboy(DadosAtualizarMotoboy dados,Long id) {
        var motoboy =repositorio.findById(id).orElseThrow(()-> new RuntimeException("Motoboy não encontrado"));

        if(repositorio.existsByCnhAndIdNot(dados.cnh(),id)){
            throw  new ValidationException("Já existe motoboy cadastrado com esse CNH");
        }
        motoboy.atualizarMotoboy(dados);
        return new DadosDetalhamentoMotoboy(motoboy);
    }

    public void desativarMotoboy(Long id) {
        if(registroKmRepositorio.existsByMotoboyIdAndKmFimIsNull(id)){
            throw new ValidationException("Motoboy possui registro de KM em aberto");
        }
        var motoboy=repositorio.getReferenceById(id);
        motoboy.desativarMotoboy();
    }

}
