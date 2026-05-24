package com.matheus.dominio.entidades;


import com.matheus.dominio.dtoEntrada.DadosAtualizarRegistroKm;
import com.matheus.dominio.dtoEntrada.DadosCadastroRegistroKm;
import jakarta.persistence.*;
import jakarta.validation.ValidationException;
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
        validarKmEntrada(dados.kmEntrada());
        validarKmSaidaAlmoco(dados.kmSaidaAlmoco(),dados.kmEntrada());
        validarKmRetornoAlmoco(dados.kmRetornoAlmoco(),dados.kmSaidaAlmoco());
        validarKmFim(dados.kmFim(),dados.kmRetornoAlmoco());
        this.motoboy=motoboy;
        this.data= dados.data();
        this.kmEntrada=dados.kmEntrada();
        this.kmSaidaAlmoco = dados.kmSaidaAlmoco();
        this.kmRetornoAlmoco = dados.kmRetornoAlmoco();
        this.kmFim=dados.kmFim();

    }

    private void validarKmEntrada(Integer kmEntrada){
        if (kmEntrada== null || kmEntrada<=0){
            throw  new ValidationException("Km entrada fornecido errado");
        }
    }
    private void validarKmSaidaAlmoco(Integer kmSaidaAlmoco, Integer kmEntrada){
        if(kmSaidaAlmoco==null || kmSaidaAlmoco<=0 || kmSaidaAlmoco < kmEntrada){
            throw new ValidationException("Km saída do almoço fornecido errado");
        }
    }
    private void validarKmRetornoAlmoco(Integer kmRetorno, Integer kmSaidaAlmoco){
        if(kmRetorno==null || kmRetorno<=0 ||  kmRetorno<kmSaidaAlmoco){
            throw new ValidationException("Km retorno almoço fornecido errado");
        }
    }
    private void validarKmFim(Integer kmFim, Integer kmRetornoAlmoco){
        if (kmFim == null || kmFim<=0 || kmFim<kmRetornoAlmoco){
            throw new ValidationException("Km fim fornecido errado");
        }
    }


    public void atualizarKm(DadosAtualizarRegistroKm dados) {

        if(dados.kmInicio()!=null){
            this.kmEntrada=dados.kmInicio();

        }
        if(dados.kmSaidaAlmoco()!=null){
            this.kmSaidaAlmoco=dados.kmSaidaAlmoco();
        }
        if(dados.kmRetornoAlmoco()!=null){
            this.kmRetornoAlmoco=dados.kmRetornoAlmoco();

        }
        if(dados.kmFim()!=null){
            this.kmFim=dados.kmFim();
        }

    }
}
