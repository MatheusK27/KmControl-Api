package com.matheus.entidades.dtoSaida;

import com.matheus.entidades.entidades.Usuario;
import com.matheus.enums.TipoUsuario;

public record DadosDetalhamentoUsuario(Long id, String nome, String login, TipoUsuario tipoUsuario) {
    public DadosDetalhamentoUsuario(Usuario usuario) {
        this(usuario.getId(),usuario.getNome(),usuario.getLogin(), usuario.getTipoUsuario());
    }
}
