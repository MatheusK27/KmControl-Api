package com.matheus.dominio.repositorio;

import com.matheus.dominio.entidades.Abastecimento;
import com.matheus.dominio.entidades.Posto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostoRepositorio  extends JpaRepository<Posto, Long> {
    List<Abastecimento> findById(Abastecimento abastecimento);
}
