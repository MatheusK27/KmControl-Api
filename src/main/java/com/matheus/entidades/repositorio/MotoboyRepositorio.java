package com.matheus.entidades.repositorio;

import com.matheus.entidades.entidades.Motoboy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MotoboyRepositorio extends JpaRepository<Motoboy, Long> {

    Page<Motoboy> findByAtivoTrue(Pageable pageable);
    Boolean existsByPlaca(String placa);

    Optional<Motoboy> findByPlaca(String placa);

    boolean existsByCnh(String cnh);

    boolean existsByCnhAndIdNot(String cnh,Long id);

    boolean existsByPlacaAndIdNot(String placa,Long id);
}
