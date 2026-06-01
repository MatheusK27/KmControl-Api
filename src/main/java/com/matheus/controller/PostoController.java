package com.matheus.controller;


import com.matheus.entidades.dtoEntrada.DadosCadastroPosto;
import com.matheus.entidades.dtoSaida.DadosDetalhamentoPosto;
import com.matheus.servico.PostoServico;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;



@SecurityRequirement(name = "bearer-key")
@RestController
@RequestMapping("/posto")
@RequiredArgsConstructor
public class PostoController {


    private final PostoServico postoServico;

    @PostMapping
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DadosDetalhamentoPosto> cadastrarPosto(@RequestBody @Valid DadosCadastroPosto dados) {
        var posto = postoServico.cadastrarPosto(dados);
        return ResponseEntity.status(HttpStatus.CREATED).body(posto);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity desativarPosto(@PathVariable Long id) {
        postoServico.desativarPosto(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoPosto> detalharPosto(Long id) {
        var posto = postoServico.detalharPosto(id);
        return ResponseEntity.ok().body(posto);
    }

}
