package com.matheus.dominio.service;

import com.matheus.dominio.dto.DadosCadastroUsuario;
import com.matheus.dominio.dto.DadosDetalhamentoUsuario;
import com.matheus.dominio.entidades.Usuario;
import com.matheus.dominio.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServico {

    @Autowired
    private UsuarioRepositorio repositorio;


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
