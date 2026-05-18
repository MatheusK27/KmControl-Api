# 🏍️ KmControl API

API REST desenvolvida com Java e Spring Boot para controle de quilometragem, abastecimento e uso de motos por motoboys.

---

## 📌 Sobre o projeto

O **KmControl API** nasceu para resolver um problema real de controle operacional: o registro manual de quilometragem das motos utilizadas por motoboys.

Em muitas empresas, o controle de KM de entrada, KM de saída, abastecimento e uso diário das motos ainda é feito em papel ou planilhas, o que aumenta o risco de erros, perda de informações e dificuldade para acompanhar os custos.

Esta API tem como objetivo centralizar esses registros em um sistema backend, permitindo maior controle sobre:

- Qual motoboy utilizou determinada moto
- Quantos quilômetros foram rodados
- Quando houve abastecimento
- Qual foi o consumo médio da moto
- Histórico de uso por moto e por motoboy

---

## 🚧 Status do projeto

Em desenvolvimento.

---

## 🎯 Objetivo

Criar uma API para substituir o controle manual de quilometragem e abastecimento das motos da empresa, oferecendo uma base organizada para futuras telas, relatórios e dashboards.

---

## ⚙️ Funcionalidades planejadas

- Cadastro de motoboys
- Cadastro de motos
- Registro de saída da moto
- Registro de retorno da moto
- Controle de KM inicial e KM final
- Cálculo automático de KM rodado
- Registro de abastecimentos
- Cálculo de média de consumo
- Histórico de uso por motoboy
- Histórico de uso por moto
- Relatórios operacionais

---

## 🧠 Regras de negócio previstas

- O KM final não pode ser menor que o KM inicial
- Cada registro de uso deve armazenar data, motoboy, moto, KM inicial e KM final
- Abastecimentos devem estar vinculados a uma moto
- A média de consumo pode ser calculada com base em KM rodado e litros abastecidos

---

## 🛠️ Tecnologias utilizadas

- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- Banco de dados relacional
- Maven
- Lombok

---

## 📁 Estrutura esperada

```bash
src/main/java
├── controller
├── service
├── repository
├── domain
├── dto
└── infra
