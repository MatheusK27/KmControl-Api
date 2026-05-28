package com.matheus.controller;

import com.matheus.dominio.dtoEntrada.DadosAtualizarAbastecimento;
import com.matheus.dominio.dtoEntrada.DadosCadastroAbastecimento;

import com.matheus.dominio.dtoEntrada.DadosFinalizarCadastroRegistroKM;
import com.matheus.dominio.dtoSaida.DadosDetalhamentoAbastecimento;
import com.matheus.dominio.dtoSaida.DadosDetalhamentoPosto;
import com.matheus.dominio.dtoSaida.DadosDetalhamentoRegistroKm;
import com.matheus.dominio.service.AbastecimentoService;
import com.matheus.dominio.service.PostoSevice;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/abastecimento")
@RequiredArgsConstructor
public class AbastecimentoController {

    private final AbastecimentoService service;


    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoAbastecimento> cadastroAbastecimento(@RequestBody @Valid DadosCadastroAbastecimento dados){
        var abastecimento = service.cadastroAbastecimento(dados);
        return ResponseEntity.status(HttpStatus.CREATED).body(abastecimento);
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
    public ResponseEntity excluirAbastecimento(@PathVariable Long id){
        service.excluirAbastecimento(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/motoboy/{id}")
    public ResponseEntity <Page<DadosDetalhamentoAbastecimento>> buscarAbastecimentosPorMotoboyId(@PathVariable Long id, Pageable pagina){
       var lista= service.buscarAbastecimentosPorMotoboyId(id, pagina);
        return ResponseEntity.ok().body(lista);
    }


    @GetMapping("/posto/{id}")
    public ResponseEntity<Page<DadosDetalhamentoAbastecimento>> detalharAbastecimentosPostoId(@PathVariable Long id,Pageable pagina){
        var lista= service.detalharAbastecimentosPostoId(id,pagina);
        return ResponseEntity.ok().body(lista);
    }

    @GetMapping("/mes")
    public ResponseEntity<Page<DadosDetalhamentoAbastecimento>> detalharAbastecimentosPorMes(@RequestParam @Min(1) @Max(12) int mes, Pageable pagina){
        var lista=service.detalharAbastecimentosPorMes(mes,pagina);
        return ResponseEntity.ok().body(lista);
    }


}
