package com.matheus.dominio.service;

import com.matheus.dominio.dtoEntrada.DadosAtualizarRegistroKm;
import com.matheus.dominio.dtoEntrada.DadosCadastroRegistroKm;
import com.matheus.dominio.dtoEntrada.DadosFinalizarCadastroRegistroKM;
import com.matheus.dominio.dtoSaida.DadosDetalhamentoRegistroKm;
import com.matheus.dominio.entidades.RegistroKm;
import com.matheus.dominio.repositorio.MotoboyRepositorio;
import com.matheus.dominio.repositorio.RegistroKmRepositorio;
import jakarta.validation.ValidationException;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class RegistroKmService {

    private final RegistroKmRepositorio repositorio;

    private final MotoboyRepositorio motoboyRepositorio;

    public DadosDetalhamentoRegistroKm cadastrarKmDiario(DadosCadastroRegistroKm dados) {
        var motoboy = motoboyRepositorio.findById(dados.motoboyId()).orElseThrow(() -> new RuntimeException(
                "Motoboy não encontrado"));
        motoboy.validarAtivo();

        if (repositorio.existsByMotoboyIdAndData(dados.motoboyId(), dados.data())) {
            throw new RuntimeException("Motoboy já possui registro nesta data");
        }
        if (repositorio.existsByMotoboyIdAndKmFimIsNull(dados.motoboyId())) {
            throw new ValidationException("Existe um km em aberto desse motoboy");
        }

        var ultimoKm = repositorio.findTopByMotoboyIdOrderByKmFimDesc(dados.motoboyId());
        if (ultimoKm.isPresent()) {

            var kmFinal = ultimoKm.get().getKmFim();

            if (dados.kmEntrada() < kmFinal) {
                throw new ValidationException("Km de inicio não pode ser menor que Km anterior");
            }
        }

        var registroKm = new RegistroKm(dados, motoboy);
        repositorio.save(registroKm);
        return new DadosDetalhamentoRegistroKm(registroKm);
    }

    public Page<DadosDetalhamentoRegistroKm> buscarRegistroKmPorMotoboyId(Long motoboyId, Pageable pagina) {
        if (!motoboyRepositorio.existsById(motoboyId)) {
            throw new ValidationException("Motoboy não encontrado");
        }
        return repositorio.findByMotoboyId(motoboyId, pagina).map(DadosDetalhamentoRegistroKm::new);
    }

    public DadosDetalhamentoRegistroKm buscarRegistroKmPorMotoboyIdEData(Long motoboyId, LocalDate data) {
        var busca = repositorio.findByMotoboyIdAndData(motoboyId, data).orElseThrow(() -> new ValidationException(
                "Não foi encontrado por id Motoboy ou data"
        ));
        return new DadosDetalhamentoRegistroKm(busca);
    }


    public DadosDetalhamentoRegistroKm atualizarKm(DadosAtualizarRegistroKm dados) {
        var km = repositorio.findById(dados.id()).orElseThrow(() -> new RuntimeException(
                "Registro KM não encontrado"));
        km.atualizarKm(dados);
        repositorio.save(km);
        return new DadosDetalhamentoRegistroKm(km);
    }


    public DadosDetalhamentoRegistroKm finalizarCadastroRegistroKm(DadosFinalizarCadastroRegistroKM dados, Long motoboyId) {
        var registroKm = repositorio.findByMotoboyIdAndKmEntradaIsNotNull(motoboyId).orElseThrow(() -> new ValidationException(
                "Motoboy precisa ter registro iniciado pra concluir finalização"
        ));
        registroKm.finalizarRegistro(dados);
        return new DadosDetalhamentoRegistroKm(registroKm);
    }
}
