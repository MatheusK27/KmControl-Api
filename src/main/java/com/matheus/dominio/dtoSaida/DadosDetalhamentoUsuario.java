package com.matheus.dominio.dtoSaida;

import com.matheus.dominio.entidades.Usuario;
import com.matheus.enums.TipoUsuario;

public record DadosDetalhamentoUsuario(Long id, String nome, String login, TipoUsuario tipoUsuario) {
    public DadosDetalhamentoUsuario(Usuario usuario) {
        this(usuario.getId(),usuario.getNome(),usuario.getLogin(), usuario.getTipoUsuario());
    }
}
