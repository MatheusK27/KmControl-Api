package com.matheus.controller;

import com.matheus.dominio.dtoEntrada.DadosAtualizarAbastecimento;
import com.matheus.dominio.dtoEntrada.DadosCadastroAbastecimento;

import com.matheus.dominio.dtoSaida.DadosDetalhamentoAbastecimento;
import com.matheus.dominio.service.AbastecimentoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/abastecimento")
public class AbastecimentoController {

    @Autowired
    private AbastecimentoService service;

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoAbastecimento> cadastroAbastecimento(@RequestBody @Valid DadosCadastroAbastecimento dados){
        var abastecimento = service.cadastroAbastecimento(dados);
        return ResponseEntity.ok().body(abastecimento);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DadosDetalhamentoAbastecimento> atualizarAbastecimento(@RequestBody @Valid DadosAtualizarAbastecimento dados,
                                                                                 @PathVariable Long id){
        var abastecimento = service.atualizarAbastecimento(dados,id);
        return ResponseEntity.ok().body(abastecimento);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void excluirAbastecimento(@PathVariable Long id){
        service.excluirAbastecimento(id);
    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity <List<DadosDetalhamentoAbastecimento>> buscarAbastecimentosPorUsuario(@PathVariable Long id){
       var lista= service.buscarAbastecimentosPorUsuario(id);
        return ResponseEntity.ok().body(lista);
    }


}
