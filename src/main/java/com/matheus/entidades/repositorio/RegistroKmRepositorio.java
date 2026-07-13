package com.matheus.entidades.repositorio;

import com.matheus.entidades.entidades.RegistroKm;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;

import java.util.List;
import java.util.Optional;

public interface RegistroKmRepositorio extends JpaRepository<RegistroKm,Long> {
    Page<RegistroKm> findByMotoboyId(Long motoboyId, Pageable pageable);
    Optional<RegistroKm> findByMotoboyIdAndData(Long motoboyId, LocalDate data);
    Optional<RegistroKm> findTopByMotoboyIdOrderByKmFimDesc(Long MotoboyId);

    boolean existsByMotoboyIdAndKmFimIsNull(Long motoboyId);
    boolean existsByMotoboyIdAndData( Long MotoboyId, LocalDate data);
    Optional<RegistroKm> findByMotoboyIdAndDataAndKmFimIsNull(Long motoboyId, LocalDate data);


    @Query("""
       SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END
       FROM RegistroKm r
       WHERE r.motoboy.id = :motoboyId
       AND r.kmFim IS NULL
       AND r.data <> :data
       """)
    boolean motoboyPossuiKmAbertoForaDaDataAtual(
            @Param("motoboyId") Long motoboyId,
            @Param("data") LocalDate data
    );

    @Query("""
       SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END
       FROM RegistroKm r
       WHERE r.motoboy.id = :motoboyId
       AND r.data = :data
       AND r.kmFim IS NULL
       """)
    boolean existeKmAbertoNaDataAtual(
            @Param("motoboyId") Long motoboyId,
            @Param("data") LocalDate data
    );

    @Query("""
       SELECT r.kmFim
       FROM RegistroKm r
       WHERE r.motoboy.id = :motoboyId
       AND r.data < :data
       AND r.kmFim IS NOT NULL
       ORDER BY r.data DESC, r.id DESC
       """)
    Optional<Integer> buscarUltimoKmFimAnterior(
            @Param("motoboyId") Long motoboyId,
            @Param("data") LocalDate data
    );

    List<RegistroKm> findByMotoboyIdAndDataBetween(
            Long motoboyId,
            LocalDate dataInicial,
            LocalDate dataFinal
    );

    @Query("""
    SELECT r
    FROM RegistroKm r
    WHERE r.motoboy.id = :motoboyId
      AND r.data BETWEEN :inicio AND :fim
    ORDER BY r.data
    """)
    List<RegistroKm> buscarRelatorioMensal(
            @Param("motoboyId") Long motoboyId,
            @Param("inicio") LocalDate inicio,
            @Param("fim") LocalDate fim
    );

    List<RegistroKm> findByKmFimIsNull();
    Optional<RegistroKm> findByMotoboyIdAndKmEntradaIsNotNull(Long motoboyId);
    List<RegistroKm> findByDataAndKmFimIsNull(LocalDate data);

}
