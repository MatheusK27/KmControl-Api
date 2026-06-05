package com.matheus.controller;


import com.matheus.entidades.dtoEntrada.DadosLogin;
import com.matheus.entidades.entidades.Usuario;
import com.matheus.seguranca.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class AutenticacaoController {

    private final TokenService tokenService;

    private final  AuthenticationManager manager;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody @Valid DadosLogin dados) {
        try {
            var token = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
            var authenticate = manager.authenticate(token);
            var jwt = tokenService.gerarToken((Usuario) authenticate.getPrincipal());
            return ResponseEntity.ok(jwt);
        } catch (BadCredentialsException e) {
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("erro", "Login ou senha inválidos"));
        }
    }
}
