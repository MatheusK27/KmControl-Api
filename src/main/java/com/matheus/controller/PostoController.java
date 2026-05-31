package com.matheus.controller;


import com.matheus.entidades.dtoEntrada.DadosCadastroPosto;
import com.matheus.entidades.dtoSaida.DadosDetalhamentoPosto;
import com.matheus.servico.PostoSevice;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posto")
@RequiredArgsConstructor
public class PostoController {


    private final PostoSevice postoSevice;

    @PostMapping
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DadosDetalhamentoPosto> cadastrarPosto(@RequestBody @Valid DadosCadastroPosto dados){
       var posto= postoSevice.cadastrarPosto(dados);
       return ResponseEntity.status(HttpStatus.CREATED).body(posto);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity desativarPosto(@PathVariable  Long id){
        postoSevice.desativarPosto(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity <DadosDetalhamentoPosto> detalharPosto(Long id){
        var posto= postoSevice.detalharPosto(id);
        return ResponseEntity.ok().body(posto);
    }

}
