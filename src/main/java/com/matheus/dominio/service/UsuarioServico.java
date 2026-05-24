package com.matheus.dominio.service;

import com.matheus.dominio.dtoEntrada.DadosCadastroUsuario;
import com.matheus.dominio.dtoSaida.DadosDetalhamentoUsuario;
import com.matheus.dominio.entidades.Usuario;
import com.matheus.dominio.repositorio.UsuarioRepositorio;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioServico {

    private final UsuarioRepositorio repositorio;


    public DadosDetalhamentoUsuario cadastrarUsuario(DadosCadastroUsuario dados){
        var usuario= new Usuario(dados);
        repositorio.save(usuario);
        return new DadosDetalhamentoUsuario(usuario);
    }


    public void excluirUsuario(Long id) {
        var usuario=  repositorio.findById(id).orElse(null);
        repositorio.delete(usuario);
    }
}
