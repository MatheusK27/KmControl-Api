package com.matheus.dominio.service;


import com.matheus.dominio.dtoEntrada.DadosAtualizarAbastecimento;
import com.matheus.dominio.dtoEntrada.DadosCadastroAbastecimento;
import com.matheus.dominio.dtoEntrada.DadosFinalizarCadastroRegistroKM;
import com.matheus.dominio.dtoSaida.DadosDetalhamentoAbastecimento;

import com.matheus.dominio.dtoSaida.DadosDetalhamentoRegistroKm;
import com.matheus.dominio.entidades.Abastecimento;


import com.matheus.dominio.repositorio.*;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;


@Service
@RequiredArgsConstructor
public class AbastecimentoService {

    private final RegistroKmRepositorio registroKmRepositorio;
    private final AbastecimentoRepositorio repositorio;
    private final MotoboyRepositorio motoboyRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;
    private final PostoRepositorio postoRepositorio;


    public DadosDetalhamentoAbastecimento cadastroAbastecimento(DadosCadastroAbastecimento dados) {

        var motoboy = motoboyRepositorio.findById(dados.motoboyId()).orElseThrow(() -> new RuntimeException(
                "Motoboy não encontrado"));

        motoboy.validarAtivo();

        var usuario = usuarioRepositorio.findById(dados.usuarioId()).orElseThrow(() -> new RuntimeException(
                "Usuario não encontrado"));

        var posto = postoRepositorio.findById(dados.postoId()).orElseThrow(() -> new RuntimeException(
                "Posto não encontrado"));

        posto.validarAtivo();

        var registroKm = registroKmRepositorio.findByMotoboyIdAndDataAndKmFimIsNull(dados.motoboyId(), dados.data()).orElseThrow(() -> new ValidationException(
                "Não existe KM aberto para esse motoboy"));


        var ultimoAbastec = repositorio.findTopByMotoboyIdOrderByKmMomentoDesc(dados.motoboyId());

        if (ultimoAbastec.isPresent()) {
            var ultimoKm = ultimoAbastec.get().getKmMomento();
            if (dados.kmMomento() < ultimoKm) {
                throw new ValidationException("KM não pode ser menor que o último abastecimento registrado ");
            }

            var autonomia=ultimoAbastec.get().getLitros().multiply(BigDecimal.valueOf(30));
            var ultimokmAbastecido= ultimoAbastec.get().getKmMomento() + autonomia.intValue()-10;
            if(dados.kmMomento()<ultimokmAbastecido){
                throw new ValidationException("Abastecimento só permitido próximo ao fim da autonomia");

            }
        }
        var abastecimento = new Abastecimento(dados, motoboy, usuario, posto);

        abastecimento.validarKmAbastecimento(registroKm);

        repositorio.save(abastecimento);

        return new DadosDetalhamentoAbastecimento(abastecimento);
    }

    public DadosDetalhamentoAbastecimento atualizarAbastecimento(DadosAtualizarAbastecimento dados, Long id) {
        var abastecimento = repositorio.findById(id).orElseThrow(() -> new RuntimeException("ID abastecimento não encontrado"));
        var registroKm= registroKmRepositorio.buscarUltimoKmFimAnterior(dados.motoboyId(), dados.data());
        if(registroKm.isPresent() && dados.kmMomento()< registroKm.get()) {
            throw new ValidationException("KM do abastecimento não pode ser menor que o último KM final anterior");

        }
        var registro= registroKmRepositorio.findById(id).orElseThrow(() -> new ValidationException("id não encontrado"));

        abastecimento.validarKmAbastecimento(registro);
        abastecimento.atualizarAbastecimento(dados);
        return new DadosDetalhamentoAbastecimento(abastecimento);
    }

    public void excluirAbastecimento(Long id) {
        var abastecimento = repositorio.findById(id).orElseThrow(() -> new RuntimeException("Abastecimento não encontrado"));
        repositorio.delete(abastecimento);

    }

    public Page<DadosDetalhamentoAbastecimento> buscarAbastecimentosPorMotoboyId(Long id, Pageable pagina) {
        return repositorio.findByMotoboyId(id, pagina).map(DadosDetalhamentoAbastecimento::new);

    }


    public Page<DadosDetalhamentoAbastecimento> detalharAbastecimentosPostoId(Long id, Pageable pagina) {
        return repositorio.findByPostoId(id, pagina).map(DadosDetalhamentoAbastecimento::new);
    }

    public Page<DadosDetalhamentoAbastecimento> detalharAbastecimentosPorMes(int mes, Pageable pagina) {
        return repositorio.buscarPorMes(mes, pagina).map(DadosDetalhamentoAbastecimento::new);


    }
}
