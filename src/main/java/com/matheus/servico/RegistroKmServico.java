package com.matheus.servico;

import com.matheus.entidades.dtoEntrada.DadosAtualizarRegistroKm;
import com.matheus.entidades.dtoEntrada.DadosCadastroRegistroKm;
import com.matheus.entidades.dtoEntrada.DadosFinalizarCadastroRegistroKM;
import com.matheus.entidades.dtoSaida.DadosDetalhamentoEmAbertoRegistroKm;
import com.matheus.entidades.dtoSaida.DadosDetalhamentoRegistroKm;
import com.matheus.entidades.entidades.RegistroKm;
import com.matheus.entidades.repositorio.AbastecimentoRepositorio;
import com.matheus.entidades.repositorio.MotoboyRepositorio;
import com.matheus.entidades.repositorio.RegistroKmRepositorio;
import com.matheus.infra.RegraDeNegocioException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RegistroKmServico {

    private final RegistroKmRepositorio repositorio;

    private final MotoboyRepositorio motoboyRepositorio;

    private final AbastecimentoRepositorio  abastecimentoRepositorio;

    public DadosDetalhamentoRegistroKm cadastrarKmDiario(DadosCadastroRegistroKm dados) {
        var motoboy = motoboyRepositorio.findById(dados.motoboyId()).orElseThrow(() -> new RegraDeNegocioException(
                "Motoboy não encontrado"));
        motoboy.validarAtivo();
        validarRegistroKm(dados);
        var registroKm = new RegistroKm(dados, motoboy);
        repositorio.save(registroKm);
        return new DadosDetalhamentoRegistroKm(registroKm);
    }

    public Page<DadosDetalhamentoRegistroKm> buscarRegistroKmPorMotoboyId(Long motoboyId, Pageable pagina) {
        if (!motoboyRepositorio.existsById(motoboyId)) {
            throw new RegraDeNegocioException("Motoboy não encontrado");
        }
        return repositorio.findByMotoboyId(motoboyId, pagina).map(DadosDetalhamentoRegistroKm::new);
    }

    public DadosDetalhamentoRegistroKm buscarRegistroKmPorMotoboyIdEData(Long motoboyId, LocalDate data) {
        var busca = repositorio.findByMotoboyIdAndData(motoboyId, data).orElseThrow(() -> new RegraDeNegocioException(
                "Não foi encontrado por id Motoboy ou data"
        ));
        return new DadosDetalhamentoRegistroKm(busca);
    }


    public DadosDetalhamentoRegistroKm atualizarKm(DadosAtualizarRegistroKm dados) {
        var km = repositorio.findById(dados.id()).orElseThrow(() -> new RegraDeNegocioException(
                "Registro KM não encontrado"));
        km.atualizarKm(dados);
        repositorio.save(km);
        return new DadosDetalhamentoRegistroKm(km);
    }


    public DadosDetalhamentoRegistroKm finalizarCadastroRegistroKm(DadosFinalizarCadastroRegistroKM dados, Long id) {
        var registroKm = repositorio.findById(id).orElseThrow(() -> new RegraDeNegocioException(
                "Motoboy precisa ter registro iniciado pra concluir finalização"
        ));
        var abastecimento= abastecimentoRepositorio.findTopByMotoboyIdOrderByKmMomentoDesc(registroKm.getMotoboy().getId());
        if(abastecimento.isPresent()) {
            var ultimoAbastecimento = abastecimento.get().getKmMomento();
            if (dados.kmFim()<ultimoAbastecimento){
                throw new RegraDeNegocioException("Km final não pode ser menor que ultimo abastecimento");
            }
        }
        registroKm.finalizarRegistro(dados);
        return new DadosDetalhamentoRegistroKm(registroKm);
    }

    public List<DadosDetalhamentoEmAbertoRegistroKm> buscarRegistrosEmAbertoHoje(){

        return repositorio.findByKmFimIsNull()
                .stream()
                .map(DadosDetalhamentoEmAbertoRegistroKm::new)
                .toList();
    }

    private void validarRegistroKm(DadosCadastroRegistroKm dados) {

        if (repositorio.existsByMotoboyIdAndData(dados.motoboyId(), dados.data())) {
            throw new RegraDeNegocioException("Motoboy já possui registro nesta data");
        }
        if (repositorio.motoboyPossuiKmAbertoForaDaDataAtual(dados.motoboyId(),dados.data())) {
            throw new RegraDeNegocioException("Existe um km em aberto desse motoboy");
        }

        var ultimoKm = repositorio.findTopByMotoboyIdOrderByKmFimDesc(dados.motoboyId());
        if (ultimoKm.isPresent()) {

            var kmFinal = ultimoKm.get().getKmFim();

            if (dados.kmEntrada() < kmFinal) {
                throw new RegraDeNegocioException("Km de inicio não pode ser menor que Km anterior");
            }
        }

    }
}
