package com.matheus.controller;


import com.matheus.dominio.dtoEntrada.DadosCadastroPosto;
import com.matheus.dominio.dtoSaida.DadosDetalhamentoPosto;
import com.matheus.dominio.service.PostoSevice;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posto")
public class PostoController {

    @Autowired
    private PostoSevice postoSevice;

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoPosto> cadastrarPosto(@RequestBody @Valid DadosCadastroPosto dados){
       var posto= postoSevice.cadastrarPosto(dados);
       return ResponseEntity.status(HttpStatus.CREATED).body(posto);
    }


}
