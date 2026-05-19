package com.matheus.seguranca;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.matheus.dominio.entidades.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String segredo;

    public String gerarToken(Usuario usuario){
        var algoritmo= Algorithm.HMAC256(segredo);
        return JWT.create().withIssuer("kmcontrol")
                .withSubject(usuario.getLogin())
                .withExpiresAt(dataExpiracao())
                .sign(algoritmo);
    }

    public String validarToken(String token){
        try {
            var algoritmo = Algorithm.HMAC256(segredo);
            return JWT.require(algoritmo)
                    .withIssuer("kmcontrol")
                    .build()
                    .verify(token)
                    .getSubject();
        }catch (JWTVerificationException exception){
            return null;
        }

    }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(3).toInstant(ZoneOffset.of("-03:00"));

    }
}


