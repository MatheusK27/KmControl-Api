package com.matheus.dominio.repositorio;

import com.matheus.dominio.entidades.Abastecimento;
import com.matheus.dominio.entidades.RegistroKm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AbastecimentoRepositorio extends JpaRepository<Abastecimento, Long> {
    Page<Abastecimento> findByMotoboyId(Long motoboyId, Pageable pageable);
    Page<Abastecimento> findByPostoId(Long postoId, Pageable pageable);

    @Query("""
            SELECT a FROM Abastecimento a 
            WHERE MONTH(a.criadoEm) = :mes
""")
    Page<Abastecimento> buscarPorMes(@Param("mes") int mes,Pageable pageable);

    Optional<Abastecimento> findTopByMotoboyIdOrderByKmMomentoDesc(Long id);




}
