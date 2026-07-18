package com.matheus.entidades.entidades;

import com.matheus.entidades.dtoEntrada.DadosCadastroPosto;
import com.matheus.infra.RegraDeNegocioException;
import jakarta.persistence.*;
import jakarta.validation.ValidationException;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
        this.nome= dados.nome();

    }

    public void validarAtivo(){
        if(!this.ativo){
            throw new RegraDeNegocioException("Posto precisar estar ativo");
        }
    }

    public void desativarPosto(Long id){
        this.ativo= false;
    }
}
