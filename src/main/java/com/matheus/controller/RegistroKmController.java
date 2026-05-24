package com.matheus.controller;

import com.matheus.dominio.dtoEntrada.DadosAtualizarAbastecimento;
import com.matheus.dominio.dtoEntrada.DadosAtualizarRegistroKm;
import com.matheus.dominio.dtoEntrada.DadosCadastroRegistroKm;
import com.matheus.dominio.dtoSaida.DadosDetalhamentoMotoboy;
import com.matheus.dominio.dtoSaida.DadosDetalhamentoRegistroKm;
import com.matheus.dominio.service.RegistroKmService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/registro")
@RequiredArgsConstructor
public class RegistroKmController {


    private final RegistroKmService servico;

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoRegistroKm> cadastrarKmDiario(@RequestBody @Valid DadosCadastroRegistroKm dados){

        var cadastro= servico.cadastrarKmDiario(dados);
        return ResponseEntity.status(HttpStatus.CREATED).body(cadastro);
    }

    @GetMapping("/motoboy/{id}")
    public ResponseEntity<Page<DadosDetalhamentoRegistroKm>>buscarRegistroKmPorMotoboyId(@PathVariable Long id, Pageable pagina){
        var busca=servico.buscarRegistroKmPorMotoboyId(id,pagina);
        return ResponseEntity.ok().body(busca);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoRegistroKm>buscarRegistroKmPorMotoboyIdEData(@PathVariable Long id, @RequestParam LocalDate data){
        return ResponseEntity.ok().body(servico.buscarRegistroKmPorMotoboyIdEData(id, data));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoRegistroKm> atualizarKm(@RequestBody @Valid DadosAtualizarRegistroKm dados){
        var atualizar= servico.atualizarKm(dados);
        return ResponseEntity.ok().body(atualizar);
    }

}
