package com.matheus.controller;

import com.matheus.dominio.dto.DadosCadastroRegistroKm;
import com.matheus.dominio.dto.DadosDetalhamentoRegistroKm;
import com.matheus.dominio.entidades.Motoboy;
import com.matheus.dominio.service.RegistroKmService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registro")
public class RegistroKmController {

    @Autowired
    private RegistroKmService servico;

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoRegistroKm> cadastrarKmDiario(@RequestBody @Valid DadosCadastroRegistroKm dados
                                                                         ){
        var cadastro= servico.cadastrarKmDiario(dados);
        return ResponseEntity.ok().body(cadastro);
    }
}
