package com.matheus.entidades.repositorio;

import com.matheus.entidades.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepositorio extends JpaRepository<Usuario,Long> {
    UserDetails findByLogin(String login);

}
