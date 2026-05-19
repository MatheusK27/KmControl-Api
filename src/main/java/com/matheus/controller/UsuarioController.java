package com.matheus.controller;


import com.matheus.dominio.dto.DadosCadastroUsuario;
import com.matheus.dominio.dto.DadosDetalhamentoMotoboy;
import com.matheus.dominio.dto.DadosDetalhamentoUsuario;
import com.matheus.dominio.entidades.Usuario;
import com.matheus.dominio.repositorio.UsuarioRepositorio;
import com.matheus.dominio.service.UsuarioServico;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepositorio repositorio;

    @Autowired
    private UsuarioServico servico;

    public ResponseEntity<DadosDetalhamentoUsuario> cadastroUsuario(@RequestBody @Valid DadosCadastroUsuario dados) {
        var usuario= servico.cadastrarUsuario(dados);
        return  ResponseEntity.ok().body(usuario);
    }
    public ResponseEntity excluirUsuario(Long id){
        servico.excluirUsuario(id);
        return ResponseEntity.notFound().build();
    }
}
