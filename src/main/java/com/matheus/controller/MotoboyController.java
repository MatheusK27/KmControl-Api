package com.matheus.controller;


import com.matheus.dominio.dto.DadosCadastroMotoboy;
import com.matheus.dominio.dto.DadosDetalhamentoMotoboy;
import com.matheus.dominio.service.MotoboyServico;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

    @RestController
    @RequestMapping("/motoboys")
    public class MotoboyController {

        @Autowired
        private MotoboyServico servico;

        @PostMapping
        public ResponseEntity<DadosDetalhamentoMotoboy> cadastrarMotoboy(@RequestBody @Valid DadosCadastroMotoboy dados){
            var motoboy= servico.cadastrarMotoboy(dados);
            return ResponseEntity.created(URI.create("/motoboys/" + motoboy.id())).body(motoboy);
        }

    @GetMapping
    public ResponseEntity<List<DadosDetalhamentoMotoboy>> listarMotoboy(){
        return ResponseEntity.ok(servico.listarMotoboy());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoMotoboy> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(servico.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desatirvarMotoboy(@PathVariable Long id){
         servico.desativarMotoboy(id);
        return ResponseEntity.noContent().build();
    }

}
