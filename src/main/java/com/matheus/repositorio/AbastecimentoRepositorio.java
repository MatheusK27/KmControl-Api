package com.matheus.repositorio;

import com.matheus.entidade.Abastecimento;
import com.matheus.entidade.RegistroKm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AbastecimentoRepositorio extends JpaRepository<RegistroKm, Long> {
    List<Abastecimento> findByMotoboyId(Long motoboyId);
    List<Abastecimento> findByDataBetween(LocalDate inicio, LocalDate fim);
}
