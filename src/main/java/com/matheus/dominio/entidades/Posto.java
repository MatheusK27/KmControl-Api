package com.matheus.dominio.entidades;

import com.matheus.dominio.dtoEntrada.DadosCadastroPosto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name= "posto")
public class Posto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cnpj;
    private String telefone;
    private String email;
    private String cidade;
    private boolean ativo= true;

    public Posto(DadosCadastroPosto dados) {
        this.id = dados.Id();
        this.nome= dados.nome();


    }

    public void desativarPosto(Long id){
        this.ativo= false;
    }
}
