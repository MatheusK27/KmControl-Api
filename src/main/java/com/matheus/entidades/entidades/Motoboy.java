package com.matheus.entidades.entidades;


import com.matheus.entidades.dtoEntrada.DadosAtualizarMotoboy;
import com.matheus.entidades.dtoEntrada.DadosCadastroMotoboy;
import com.matheus.infra.RegraDeNegocioException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "motoboy")
public class Motoboy {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true, nullable = false)
    private String cnh;

    @Column(unique = true, nullable = false)
    private String placa;

    private String telefone;

    private Boolean ativo= true;

    private LocalDateTime criadoEm= LocalDateTime.now();

    public Motoboy(DadosCadastroMotoboy dados) {
        this.nome= dados.nome();
        this.cnh= dados.cnh();
        this.telefone= dados.telefone();
        this.placa= dados.placa();

    }

    public void validarAtivo(){
        if(!this.ativo){
            throw new RegraDeNegocioException("Motoboy inativo");
        }
    }

    public void atualizarMotoboy(DadosAtualizarMotoboy dados) {
        if (dados.nome()!=null) {
            this.nome= dados.nome();
        }
        if (dados.cnh()!=null) {
            this.cnh= dados.cnh();
        }
        if (dados.placa()!=null) {
            this.placa= dados.placa();
        }
        if (dados.telefone()!=null) {
            this.telefone= dados.telefone();
        }
    }
    public void desativarMotoboy() {
        this.ativo= false;
    }


}

