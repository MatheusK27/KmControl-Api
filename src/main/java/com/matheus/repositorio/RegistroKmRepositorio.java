package com.matheus.repositorio;

import com.matheus.entidade.Motoboy;
import com.matheus.entidade.RegistroKm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RegistroKmRepositorio extends JpaRepository<RegistroKm,Long> {
    List<RegistroKm> findByMotoboyId(Long motoboyId);
    List<RegistroKm> findByData(LocalDate data);
    Optional<RegistroKm> findByMotoboyIdAndData(Long id, LocalDate data);
}
