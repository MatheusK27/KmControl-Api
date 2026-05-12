package com.matheus.entidade;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "motoboys")
public class Motoboy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cnh;
    private String telefone;
    private Boolean ativo= true;
    private LocalDateTime criadoEm= LocalDateTime.now();
}
