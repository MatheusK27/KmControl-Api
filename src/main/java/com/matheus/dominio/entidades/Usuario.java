package com.matheus.dominio.entidades;


import com.matheus.dominio.dtoEntrada.DadosCadastroUsuario;
import com.matheus.enums.TipoUsuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="usuario")
public class Usuario  implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String senha;

        @Column(name = "tipo_usuario")
        @Enumerated(EnumType.STRING)
        private TipoUsuario tipoUsuario;

    private boolean ativo = true;

    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm =  LocalDateTime.now();

    public Usuario(DadosCadastroUsuario dados) {
        this.nome= dados.nome();
        this.login = dados.login();
        this.senha = new BCryptPasswordEncoder().encode(dados.senha());
        this.tipoUsuario= TipoUsuario.USUARIO;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of( new SimpleGrantedAuthority("ROLE_" + this.tipoUsuario.name()));
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.login;
    }
}
