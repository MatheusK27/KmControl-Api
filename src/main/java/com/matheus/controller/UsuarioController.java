package com.matheus.controller;


import com.matheus.dominio.dtoEntrada.DadosCadastroUsuario;
import com.matheus.dominio.dtoSaida.DadosDetalhamentoUsuario;
import com.matheus.dominio.repositorio.UsuarioRepositorio;
import com.matheus.dominio.service.UsuarioServico;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<DadosDetalhamentoUsuario> cadastroUsuario(@RequestBody @Valid DadosCadastroUsuario dados) {
        var usuario= servico.cadastrarUsuario(dados);
        return  ResponseEntity.ok().body(usuario);
    }
    @DeleteMapping("/excluir/{id}")
    public ResponseEntity excluirUsuario(@PathVariable Long id){
        servico.excluirUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
