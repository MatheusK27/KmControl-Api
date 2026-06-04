package com.matheus.servico;


import com.matheus.entidades.dtoEntrada.DadosCadastroPosto;
import com.matheus.entidades.dtoSaida.DadosDetalhamentoPosto;
import com.matheus.entidades.entidades.Posto;
import com.matheus.entidades.repositorio.AbastecimentoRepositorio;
import com.matheus.entidades.repositorio.PostoRepositorio;
import com.matheus.infra.RegraDeNegocioException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostoServico {


    private final PostoRepositorio repositorio;

    public DadosDetalhamentoPosto cadastrarPosto(DadosCadastroPosto dados) {
        validarCnpj(dados.cnpj());
        validarEmail(dados.email());
        var posto = new Posto(dados);
        repositorio.save(posto);
        return new DadosDetalhamentoPosto(posto);
    }

    public void desativarPosto(Long id){
        var posto= repositorio.findById(id).orElseThrow(()-> new RuntimeException("Posto não encontrado"));
        posto.desativarPosto(id);
        repositorio.save(posto);
    }

    public DadosDetalhamentoPosto detalharPosto(Long postoId){
        var posto= repositorio.findById(postoId).orElseThrow(()-> new RuntimeException("Posto não cadastrado"));
        return new DadosDetalhamentoPosto(posto);
    }

    private void validarCnpj(String cnpj){
        if (repositorio.existsByCnpj(cnpj)){
            throw new RegraDeNegocioException("Já existe cadastro com esse cnpj");
        }
    }
    private void validarEmail(String email){
        if (repositorio.existsByEmail(email)){
            throw new RegraDeNegocioException("Já existe cadastro com esse email");
        }
    }

}
