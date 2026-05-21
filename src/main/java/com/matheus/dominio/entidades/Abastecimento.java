    package com.matheus.dominio.entidades;


    import com.matheus.dominio.dtoEntrada.DadosAtualizarAbastecimento;
    import com.matheus.dominio.dtoEntrada.DadosCadastroAbastecimento;
    import jakarta.persistence.*;
    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    import java.math.BigDecimal;
    import java.time.LocalDate;
    import java.time.LocalDateTime;

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


        public Abastecimento(DadosCadastroAbastecimento dados, Motoboy motoboy,Usuario usuario ) {
            this.motoboy=motoboy;
            this.usuario=usuario;
            this.data= dados.data();
            this.kmMomento=dados.kmMomento();
            this .litros = dados.litros();
            this.valorLitro=dados.valorLitro();

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

    }


