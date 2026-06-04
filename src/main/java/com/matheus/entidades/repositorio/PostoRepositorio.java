package com.matheus.entidades.repositorio;

import com.matheus.entidades.entidades.Posto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostoRepositorio  extends JpaRepository<Posto, Long> {
    Optional<Posto> findById(Long id);
    boolean existsByCnpj(String cnpj);
    boolean existsByEmail(String email);
}
