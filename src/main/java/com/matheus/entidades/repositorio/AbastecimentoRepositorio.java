package com.matheus.entidades.repositorio;

import com.matheus.entidades.entidades.Abastecimento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AbastecimentoRepositorio extends JpaRepository<Abastecimento, Long> {
    Page<Abastecimento> findByMotoboyId(Long motoboyId, Pageable pageable);
    Page<Abastecimento> findByPostoId(Long postoId, Pageable pageable);


    @Query("""
            SELECT a FROM Abastecimento a 
            WHERE MONTH(a.criadoEm) = :mes
""")
    Page<Abastecimento> buscarPorMes(@Param("mes") int mes,Pageable pageable);
    Optional<Abastecimento> findTopByMotoboyIdOrderByKmMomentoDesc(Long motoboyId);




}
