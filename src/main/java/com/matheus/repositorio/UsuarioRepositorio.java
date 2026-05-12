package com.matheus.repositorio;

import com.matheus.entidade.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepositorio extends JpaRepository<Usuario,Long> {
    Usuario findByLogin(String login);

}
