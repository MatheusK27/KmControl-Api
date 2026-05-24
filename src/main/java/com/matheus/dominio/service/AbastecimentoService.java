package com.matheus.dominio.service;


import com.matheus.dominio.dtoEntrada.DadosAtualizarAbastecimento;
import com.matheus.dominio.dtoEntrada.DadosCadastroAbastecimento;
import com.matheus.dominio.dtoSaida.DadosDetalhamentoAbastecimento;

import com.matheus.dominio.entidades.Abastecimento;

import com.matheus.dominio.repositorio.AbastecimentoRepositorio;
import com.matheus.dominio.repositorio.MotoboyRepositorio;
import com.matheus.dominio.repositorio.PostoRepositorio;
import com.matheus.dominio.repositorio.UsuarioRepositorio;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
@RequiredArgsConstructor
public class AbastecimentoService {

    private final AbastecimentoRepositorio repositorio;


    private final MotoboyRepositorio motoboyRepositorio;

    private final UsuarioRepositorio usuarioRepositorio;


    private final PostoRepositorio postoRepositorio;


    public DadosDetalhamentoAbastecimento cadastroAbastecimento(DadosCadastroAbastecimento dados) {
        var ultimoAbastec = repositorio.findTopByMotoboyIdOrderByKmMomentoDesc(dados.motoboyId());
        if(ultimoAbastec.isPresent()){
            var ultimoKm = ultimoAbastec.get().getKmMomento();
            if(dados.kmMomento()<ultimoKm){
                 throw new ValidationException("KM não pode ser menor que o último abastecimento registrado ");
            }
        }

        if(!motoboyRepositorio.existsById(dados.motoboyId())){
            throw new RuntimeException("Motoboy inativo");
        }
        var motoboy= motoboyRepositorio.findById(dados.motoboyId()).orElseThrow(() -> new RuntimeException("Motoboy não encontrado"));
        var usuario= usuarioRepositorio.findById(dados.usuarioId()).orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
        var posto = postoRepositorio.findById(dados.postoId()).orElseThrow(() -> new RuntimeException("Posto não encontrado"));
        if (!posto.isAtivo()){
            throw new RuntimeException("Não é possivel cadastrar abastecimento com posto inativo");
        }
        var abastecimento= new Abastecimento(dados, motoboy,usuario,posto);
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

    public void excluirAbastecimento(Long id ) {
       var abastecimento= repositorio.findById(id).orElseThrow(()-> new RuntimeException("Abastecimento não encontrado"));
       repositorio.delete(abastecimento);

    }

    public Page<DadosDetalhamentoAbastecimento> buscarAbastecimentosPorMotoboyId(Long id, Pageable pagina) {
        return repositorio.findByMotoboyId(id,pagina).map(DadosDetalhamentoAbastecimento::new);

    }


    public Page<DadosDetalhamentoAbastecimento> detalharAbastecimentosPostoId(Long id,  Pageable pagina) {
        return repositorio.findByPostoId(id,pagina).map(DadosDetalhamentoAbastecimento::new);
    }
    public Page<DadosDetalhamentoAbastecimento> detalharAbastecimentosPorMes(int mes, Pageable pagina) {
        var lista= repositorio.buscarPorMes(mes,pagina).map(DadosDetalhamentoAbastecimento::new);
        return lista;

    }

    private double calculoCombustivel(Abastecimento abastecimento) {
        BigDecimal valorTotalCombustivel = abastecimento.getValorLitro().multiply(abastecimento.getLitros());
        return valorTotalCombustivel.doubleValue();
    }

}
