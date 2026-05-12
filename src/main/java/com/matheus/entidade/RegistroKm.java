package com.matheus.entidade;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="registro_km")
public class RegistroKm {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
        @JoinColumn(name = "motoboy_id", nullable = false)
    private Motoboy motoboy;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    private LocalDate data= LocalDate.now();

    private Integer kmInicio;

    private Integer kmSaidaAlmoco;

    private Integer kmEntredaAlmoco;

    private Integer kmFim;

    private String observacao;

    private LocalDate criadoEm= LocalDate.now();







}
