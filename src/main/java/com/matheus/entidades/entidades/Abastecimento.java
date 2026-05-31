    package com.matheus.entidades.entidades;


    import com.matheus.entidades.dtoEntrada.DadosAtualizarAbastecimento;
    import com.matheus.entidades.dtoEntrada.DadosCadastroAbastecimento;
    import com.matheus.infra.RegraDeNegocioException;
    import jakarta.persistence.*;
    import jakarta.validation.ValidationException;
    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    import java.math.BigDecimal;
    import java.time.LocalDate;
    import java.time.LocalDateTime;
    import java.util.Objects;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Entity
    @Table(name="abastecimento")
    public class Abastecimento {



        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "motoboy_id", nullable = false)
        private Motoboy motoboy;

        @ManyToOne
        @JoinColumn(name = "usuario_id", nullable = false)
        private Usuario usuario;

        @ManyToOne
        @JoinColumn(name = "posto_id")
        private Posto posto;

        private LocalDate data = LocalDate.now();

        private Integer  kmMomento;

        private BigDecimal litros;

        private BigDecimal valorLitro;

        private BigDecimal valorTotal;

        private LocalDateTime criadoEm = LocalDateTime.now();


        public Abastecimento(DadosCadastroAbastecimento dados, Motoboy motoboy,Usuario usuario,  Posto posto ) {
            this.motoboy=motoboy;
            this.usuario=usuario;
            this.data= dados.data();
            this.posto= posto;
            this.kmMomento=dados.kmMomento();
            this.litros = dados.litros();
            this.valorLitro=dados.valorLitro();
            validarAbastecimento();

        }

          private  void validarAbastecimento(){

            BigDecimal limiteMaximo=BigDecimal.valueOf(8);

            if(litros.compareTo(limiteMaximo)>0){
                throw new ValidationException("Limite de abastecimento excedido, permitido somente 8 litros de combustivél ");
            }
            if(litros.compareTo(BigDecimal.ZERO)<=0){
                throw new ValidationException("Litros deve ser maior que zero");
            }

            if (!Objects.equals(getData(), LocalDate.now())){
                throw  new ValidationException("Permito abastecimento somente data atual");

            }

              if (valorLitro.compareTo(BigDecimal.ZERO)<=0){
                throw new RuntimeException("Valor litro inserio errado");

            }

        }

        public void validarKmAbastecimento(RegistroKm registroKm){
            if(this.kmMomento < registroKm.getKmEntrada()){
                throw new RegraDeNegocioException("Km de abastecimento não pode ser menor que km de entrada");
            }
            if(registroKm.getKmFim()!=null && this.kmMomento > registroKm.getKmFim()){
                throw new RegraDeNegocioException("Km de abastecimento não pode ser maior que km final");
            }

        }


        public void atualizarAbastecimento(DadosAtualizarAbastecimento dados){
            if (dados.data()!=null){
                this.data=dados.data();
            }
            if (dados.kmMomento()!=null){
                this.kmMomento=dados.kmMomento();
            }
            if (dados.litros()!=null){
                this.litros=dados.litros();
            }
            if (dados.valorLitro()!=null){
                this.valorLitro=dados.valorLitro();
            }

        }

        public BigDecimal calculoCombustivel() {
             return getValorLitro().multiply(getLitros());
        }


    }


