package com.matheus.dominio.dtoSaida;

import com.matheus.dominio.entidades.Usuario;

public record DadosDetalhamentoUsuario(Long id, String nome, String login) {
    public DadosDetalhamentoUsuario(Usuario usuario) {
        this(usuario.getId(),usuario.getNome(),usuario.getLogin());
    }
}
