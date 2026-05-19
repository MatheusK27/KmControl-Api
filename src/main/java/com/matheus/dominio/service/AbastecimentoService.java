package com.matheus.dominio.service;


import com.matheus.dominio.entidades.Abastecimento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AbastecimentoService {

    @Autowired
    private Abastecimento abastecimento;
}
