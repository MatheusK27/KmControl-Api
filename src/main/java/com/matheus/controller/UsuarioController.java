package com.matheus.controller;


import com.matheus.dominio.dtoEntrada.DadosCadastroUsuario;
import com.matheus.dominio.dtoSaida.DadosDetalhamentoUsuario;
import com.matheus.dominio.repositorio.UsuarioRepositorio;
import com.matheus.dominio.service.UsuarioServico;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepositorio repositorio;

    @Autowired
    private UsuarioServico servico;

    @PostMapping
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
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
