package com.matheus.entidade;


import jakarta.persistence.*;
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

    private Integer kmMomento;

    private BigDecimal litros;

    private BigDecimal valorTotal;

    private String posto;

    private LocalDateTime criadoEm = LocalDateTime.now();
}
