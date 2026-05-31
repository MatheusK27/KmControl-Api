package com.matheus.controller;


import com.matheus.entidades.dtoEntrada.DadosCadastroUsuario;
import com.matheus.entidades.dtoSaida.DadosDetalhamentoUsuario;
import com.matheus.servico.UsuarioServico;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioServico servico;

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoUsuario> cadastroUsuario(@RequestBody @Valid DadosCadastroUsuario dados) {
        var usuario= servico.cadastrarUsuario(dados);
        return  ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }
    @DeleteMapping("/excluir/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity excluirUsuario(@PathVariable Long id){
        servico.excluirUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
