package com.matheus.dominio.entidades;


import com.matheus.dominio.dto.DadosCadastroRegistroKm;
import com.matheus.dominio.dto.DadosDetalhamentoRegistroKm;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

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


    private LocalDate data= LocalDate.now();

    private Integer kmEntrada;

    private Integer kmSaidaAlmoco;

    private Integer kmRetornoAlmoco;

    private Integer kmFim;

    private String observacao;

    private LocalDateTime criadoEm= LocalDateTime.now();

    public RegistroKm (DadosCadastroRegistroKm dados,Motoboy motoboy){
        this.motoboy=motoboy;
        this.data= dados.data();
        this.kmEntrada=dados.kmInicio();
        this.kmSaidaAlmoco = dados.kmSaidaAlmoco();
        this.kmRetornoAlmoco = dados.kmRetornoAlmoco();
        this.kmFim=dados.kmFim();

    }



}
