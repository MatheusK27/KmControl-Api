package com.matheus.dominio.repositorio;

import com.matheus.dominio.entidades.Motoboy;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MotoboyRepositorio extends JpaRepository<Motoboy, Long> {

    List<Motoboy> findByAtivoTrue();
    Boolean existsByPlaca(String placa);

    Optional<Motoboy> findByPlaca(String placa);

    boolean existsByCnh(String cnh);

}
