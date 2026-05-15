package com.matheus.dominio.entidades;


import com.matheus.dominio.dto.DadosCadastroRegistroKm;
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

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    private LocalDate data= LocalDate.now();

    private Integer kmEntrada;

    private Integer kmSaidaAlmoco;

    private Integer kmRetornoAlmoco;

    private Integer kmFim;

    private String observacao;

    private LocalDateTime criadoEm= LocalDateTime.now();

    private RegistroKm (DadosCadastroRegistroKm dados,Motoboy motoboy,Usuario usuario){
        this.motoboy=motoboy;
        this.usuario=usuario;
        this.data= dados.data();
        this.kmEntrada=dados.kmInicio();
        this.kmSaidaAlmoco = dados.kmSaidaAlmoco();
        this.kmRetornoAlmoco = dados.kmRetornoAlmoco();
        this.kmFim=dados.kmFim();
        this.observacao=dados.observacao();
    }



}
