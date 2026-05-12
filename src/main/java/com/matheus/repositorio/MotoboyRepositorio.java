package com.matheus.repositorio;

import com.matheus.entidade.Motoboy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MotoboyRepositorio extends JpaRepository<Motoboy, Long> {

    List<Motoboy> findByAtivoTrue();
}
