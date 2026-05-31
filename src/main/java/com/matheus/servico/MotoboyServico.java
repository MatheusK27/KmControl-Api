package com.matheus.servico;


import com.matheus.entidades.dtoEntrada.DadosAtualizarMotoboy;
import com.matheus.entidades.dtoEntrada.DadosCadastroMotoboy;
import com.matheus.entidades.dtoSaida.DadosDetalhamentoMotoboy;
import com.matheus.entidades.entidades.Motoboy;
import com.matheus.entidades.repositorio.MotoboyRepositorio;
import com.matheus.entidades.repositorio.RegistroKmRepositorio;
import com.matheus.infra.RegraDeNegocioException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class    MotoboyServico {

    private final MotoboyRepositorio repositorio;

    private final RegistroKmRepositorio registroKmRepositorio;


    public  DadosDetalhamentoMotoboy cadastrarMotoboy(DadosCadastroMotoboy dados) {

    if(repositorio.existsByPlaca(dados.placa())){
            throw  new RegraDeNegocioException("Placa já cadastrada");
    }

        if(repositorio.existsByCnh(dados.cnh())){
        throw  new RegraDeNegocioException("Já existe motoboy cadastrado com essa CNH");
    }
        var motoboy = new Motoboy(dados);
        repositorio.save(motoboy);
        return new DadosDetalhamentoMotoboy(motoboy);
    }


    public Page<DadosDetalhamentoMotoboy> listarMotoboy(Pageable pagina ) {
        return repositorio.findByAtivoTrue(pagina).map(DadosDetalhamentoMotoboy::new);
    }

    public DadosDetalhamentoMotoboy buscarPorId(Long id) {
        var motoboy = repositorio.findById(id).orElseThrow(()->new RegraDeNegocioException("Motoboy não encontrado"));
        return new DadosDetalhamentoMotoboy(motoboy);
    }
    public DadosDetalhamentoMotoboy buscarMotoboyPorPlaca(String placa) {
        var motoboy= repositorio.findByPlaca(placa).orElseThrow(()-> new RegraDeNegocioException("Motoboy não encontrado pela placa"));
        return new DadosDetalhamentoMotoboy(motoboy);
    }

    public DadosDetalhamentoMotoboy atualizarMotoboy(DadosAtualizarMotoboy dados,Long id) {
        var motoboy =repositorio.findById(id).orElseThrow(()-> new RegraDeNegocioException("Motoboy não encontrado"));

        if(dados.cnh()!=null && repositorio.existsByCnhAndIdNot(dados.cnh(),id)){
            throw  new RegraDeNegocioException("Já existe motoboy cadastrado com esse CNH");
        }

        if(dados.placa()!=null && repositorio.existsByPlacaAndIdNot(dados.placa(),id)){
            throw new RegraDeNegocioException("J existe motoboy com esse placa");
        }
        motoboy.atualizarMotoboy(dados);
        return new DadosDetalhamentoMotoboy(motoboy);
    }

    public void desativarMotoboy(Long id) {
        if(registroKmRepositorio.existsByMotoboyIdAndKmFimIsNull(id)){
            throw new RegraDeNegocioException("Motoboy possui registro de KM em aberto");
        }
        var motoboy=repositorio.getReferenceById(id);
        motoboy.desativarMotoboy();
    }

}
