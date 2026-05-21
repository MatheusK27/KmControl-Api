package com.matheus.controller;


import com.matheus.dominio.dtoEntrada.DadosLogin;
import com.matheus.dominio.entidades.Usuario;
import com.matheus.seguranca.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager manager;

    @PostMapping
    public ResponseEntity<String> login(@RequestBody @Valid DadosLogin dados) {
        var token= new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var authenticate = manager.authenticate(token);
        var jwt= tokenService.gerarToken((Usuario) authenticate.getPrincipal());
        return ResponseEntity.ok(jwt);
    }
}
