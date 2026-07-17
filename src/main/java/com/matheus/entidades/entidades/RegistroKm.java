package com.matheus.entidades.entidades;


import com.matheus.entidades.dtoEntrada.DadosAtualizarRegistroKm;
import com.matheus.entidades.dtoEntrada.DadosCadastroRegistroKm;
import com.matheus.entidades.dtoEntrada.DadosFinalizarCadastroRegistroKM;
import com.matheus.infra.RegraDeNegocioException;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "registro_km")
public class RegistroKm {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "motoboy_id", nullable = false)
    private Motoboy motoboy;


    private LocalDate data = LocalDate.now();

    private Integer kmEntrada;

    private Integer kmSaidaAlmoco;

    private Integer kmRetornoAlmoco;

    private Integer kmFim;

    private Integer totalKm;

    private String observacao;

    private LocalDateTime criadoEm = LocalDateTime.now();

    public RegistroKm(DadosCadastroRegistroKm dados, Motoboy motoboy) {
        validarKmEntrada(dados.kmEntrada(),dados.data());
        this.motoboy = motoboy;
        this.data = dados.data();
        this.kmEntrada = dados.kmEntrada();
        this.kmSaidaAlmoco = dados.kmSaidaAlmoco();
        this.kmRetornoAlmoco = dados.kmRetornoAlmoco();
        this.kmFim = dados.kmFim();
        calcularTotalKm();


    }

    private void validarKmEntrada(Integer kmEntrada,LocalDate data) {
        if (kmEntrada == null || kmEntrada <= 0) {
            throw new RegraDeNegocioException("Km entrada não fornecido ou menor que zero");
        }
       /* if (!Objects.equals(data, LocalDate.now())){
            throw new RegraDeNegocioException("Permitido somente data atual!");
        }*/
    }

    public void atualizarKm(DadosAtualizarRegistroKm dados) {

        if (dados.kmInicio() != null) {
            this.kmEntrada = dados.kmInicio();
        }
        if (dados.kmSaidaAlmoco() != null) {
            this.kmSaidaAlmoco = dados.kmSaidaAlmoco();
        }
        if (dados.kmRetornoAlmoco() != null) {
            this.kmRetornoAlmoco = dados.kmRetornoAlmoco();
        }
        if (dados.kmFim() != null) {
            this.kmFim = dados.kmFim();
        }
        validarRegistroCompleto();
        calcularTotalKm();

    }

    private void validarRegistroCompleto() {

        if (kmEntrada == null) {
            throw new RegraDeNegocioException("KM de entrada é obrigatório");
        }

        if (kmSaidaAlmoco != null && kmSaidaAlmoco < kmEntrada) {
            throw new RegraDeNegocioException("KM de saída para almoço não pode ser menor que KM de entrada");
        }

        if (kmRetornoAlmoco != null && kmSaidaAlmoco == null) {
            throw new RegraDeNegocioException("KM de retorno do almoço exige KM de saída para almoço");
        }

        if (kmRetornoAlmoco != null && kmRetornoAlmoco < kmSaidaAlmoco) {
            throw new RegraDeNegocioException("KM de retorno do almoço não pode ser menor que KM de saída para almoço");
        }

        if (kmFim != null) {
            Integer kmAnterior = kmRetornoAlmoco != null ? kmRetornoAlmoco : kmEntrada;

            if (kmFim < kmAnterior) {
                throw new RegraDeNegocioException("KM final não pode ser menor que o KM anterior");
            }
        }
    }


    public void finalizarRegistro(DadosFinalizarCadastroRegistroKM dados) {
        if(this.kmEntrada == null) {
            throw new RegraDeNegocioException("Pra finalizar o km de entrada precisa ser informada");
        }
        if(dados.kmSaidaAlmoco()< kmEntrada){
            throw new RegraDeNegocioException("Km de saída do almoço não pode ser menor que km de entrada");
        }

        if (dados.kmRetornoAlmoco() < dados.kmSaidaAlmoco()) {
            throw new RegraDeNegocioException("Km de retorno não pode ser menor que Km de saida almoco");
        }

        if (dados.kmFim() < dados.kmRetornoAlmoco()) {
            throw new RegraDeNegocioException("Km de saida não pode ser menor que Km de retorno do almoço");
        }
        if (this.kmFim != null) {
            throw new RegraDeNegocioException("Registro já foi finalizado");
        }
       if(dados.kmFim() - kmEntrada >= 500){
            throw new RegraDeNegocioException("Não é permitdo andar mais de 500km diarios");
        }

        this.kmSaidaAlmoco = dados.kmSaidaAlmoco();
        this.kmRetornoAlmoco = dados.kmRetornoAlmoco();
        this.kmFim = dados.kmFim();
        calcularTotalKm();

    }
    private void calcularTotalKm() {
        if (this.kmFim != null && this.kmEntrada != null) {
            this.totalKm = this.kmFim - this.kmEntrada;
        }
    }

    }