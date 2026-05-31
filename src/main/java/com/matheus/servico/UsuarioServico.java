package com.matheus.servico;

import com.matheus.entidades.dtoEntrada.DadosCadastroUsuario;
import com.matheus.entidades.dtoSaida.DadosDetalhamentoUsuario;
import com.matheus.entidades.entidades.Usuario;
import com.matheus.entidades.repositorio.UsuarioRepositorio;
import lombok.RequiredArgsConstructor;
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
