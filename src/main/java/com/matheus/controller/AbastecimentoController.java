package com.matheus.controller;

import com.matheus.dominio.dto.DadosCadastroAbastecimento;
import com.matheus.dominio.dto.DadosDetalhamentoAbastecimento;
import com.matheus.dominio.entidades.Abastecimento;
import com.matheus.dominio.repositorio.AbastecimentoRepositorio;
import com.matheus.dominio.service.AbastecimentoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/abastecimento")
public class AbastecimentoController {

    @Autowired
    private AbastecimentoService service;

    @Autowired
    private AbastecimentoRepositorio repositorio;

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoAbastecimento> fornecerAbastecimento(@RequestBody @Valid DadosCadastroAbastecimento dados){
        var abastecimento = service.fornecerAbastecimento(dados);
        return ResponseEntity.ok().body(abastecimento);

    }
}
