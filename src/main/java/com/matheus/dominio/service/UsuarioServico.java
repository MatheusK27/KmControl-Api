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

    @Autowired
    private Usuario usuario;

    public DadosDetalhamentoUsuario cadastrarUsuario(DadosCadastroUsuario dados){
        var usuario= new Usuario(dados);
        repositorio.save(usuario);
        return new DadosDetalhamentoUsuario(usuario);
    }

    public void excluirUsuario(Long id){
        if (usuario.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            repositorio.deleteById(id);
        }  else {
            throw new RuntimeException("Usuário não possui permissão para excluir");
        }

    }

}
