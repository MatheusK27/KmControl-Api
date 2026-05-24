package com.matheus.dominio.repositorio;

import com.matheus.dominio.entidades.RegistroKm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RegistroKmRepositorio extends JpaRepository<RegistroKm,Long> {
    Page<RegistroKm> findByMotoboyId(Long motoboyId, Pageable pageable);
    Page<RegistroKm> findByData(LocalDate data, Pageable pageable);
    Optional<RegistroKm> findByMotoboyIdAndData(Long id, LocalDate data);
    Optional<RegistroKm> findTopByMotoboyIdOrderByKmFimDesc(Long MotoboyId);

    boolean existsByMotoboyIdAndKmFimIsNull(Long id);
    boolean existsByMotoboyIdAndData( Long MotoboyId, LocalDate data);
}
