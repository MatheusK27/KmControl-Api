package com.matheus.controller;


import com.matheus.entidades.dtoEntrada.DadosAtualizarMotoboy;
import com.matheus.entidades.dtoEntrada.DadosCadastroMotoboy;
import com.matheus.entidades.dtoSaida.DadosDetalhamentoMotoboy;
import com.matheus.servico.MotoboyServico;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;

@RestController
    @RequestMapping("/motoboys")
    @RequiredArgsConstructor
    public class MotoboyController {

        private final MotoboyServico servico;

        @PostMapping
        public ResponseEntity<DadosDetalhamentoMotoboy> cadastrarMotoboy(@RequestBody @Valid DadosCadastroMotoboy dados){
            var motoboy= servico.cadastrarMotoboy(dados);
            return ResponseEntity.status(HttpStatus.CREATED).body(motoboy);
        }

    @GetMapping
    public ResponseEntity<Page<DadosDetalhamentoMotoboy>> listarMotoboy(Pageable pagina){
        return ResponseEntity.ok(servico.listarMotoboy(pagina));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoMotoboy> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(servico.buscarPorId(id));
    }
    @GetMapping("/placa")
    public ResponseEntity<DadosDetalhamentoMotoboy> buscarMotoboyPorPlaca( @RequestParam String placa ){
            return ResponseEntity.ok(servico.buscarMotoboyPorPlaca(placa));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoMotoboy> atualizarMotoboy (@PathVariable Long id, @RequestBody DadosAtualizarMotoboy dados){
            return ResponseEntity.ok(servico.atualizarMotoboy(dados,id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> desatirvarMotoboy(@PathVariable Long id){
         servico.desativarMotoboy(id);
        return ResponseEntity.noContent().build();
    }

}
