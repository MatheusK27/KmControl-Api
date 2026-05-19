package com.matheus.dominio.entidades;


import com.matheus.dominio.dto.DadosCadastroMotoboy;
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
    private String cnh;
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
}
