package com.matheus.dominio.entidades;


import com.matheus.dominio.dto.DadosCadastroAbastecimento;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Abastecimento")
public class Abastecimento {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "motoboy_id", nullable = false)
    private Motoboy motoboy;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    private LocalDate data = LocalDate.now();

    private Integer  kmMomento;


    private BigDecimal litros;

    private BigDecimal valorLitro;

    private BigDecimal valorTotal;

    private String posto;

    private LocalDateTime criadoEm = LocalDateTime.now();


    public Abastecimento(DadosCadastroAbastecimento dados, Motoboy motoboy) {
        this.motoboy=motoboy;
        this.data= dados.data();
        this.kmMomento=dados.kmMomento();
        this .litros = dados.litros();
        this.valorLitro=dados.valorLitro();
        this.posto=dados.posto();


    }


}


