package com.matheus.infra;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(RegraDeNegocioException.class)
    public ResponseEntity<DadosErro> tratarRegraDeNegocio(RegraDeNegocioException ex) {
        return ResponseEntity.badRequest().body(new DadosErro(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity <List<DadosErroValidacao>> tratarErro400(MethodArgumentNotValidException ex) {
        var erro = ex.getFieldErrors();
        return ResponseEntity.badRequest()
                .body(erro.stream().map(DadosErroValidacao::new).toList());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Void> tratarErro404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, String>> tratarCredenciaisInvalidas(BadCredentialsException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("erro", "Login ou senha inválidos"));
    }


    private record DadosErro (String mensagem){}

    private record DadosErroValidacao (String campo ,String mensagem){
        public DadosErroValidacao(FieldError erro){
            this(erro.getField(), erro.getDefaultMessage());
        }
    }

}