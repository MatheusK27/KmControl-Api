# 🚀 KmControl API

API REST desenvolvida para controle de operações de motoboys, abastecimentos e registros de quilometragem.

O sistema foi criado para resolver um problema real de controle manual de:
- 🛵 KM inicial e final das motos
- ⛽ Abastecimentos
- 📊 Consumo de combustível
- 📋 Controle operacional de motoboys

🌐 **Acesso:** [http://18.228.117.80:8080/kmcontrol.html](http://18.228.117.80:8080/kmcontrol.html)

---

## 🛠️ Tecnologias utilizadas

- ☕ Java 21
- 🌱 Spring Boot 3.5
- 🔐 Spring Security + JWT
- 🗄️ Spring Data JPA + Hibernate
- 🐘 PostgreSQL 16
- 📦 Maven
- ✅ Bean Validation
- 🐳 Docker + Docker Compose
- ☁️ AWS EC2 (deploy em produção)
- 🗃️ Flyway (migrations)
- 📄 OpenPDF (geração de relatórios PDF)
- 📚 Swagger / OpenAPI (documentação)

---

## ✨ Funcionalidades

### 🛵 Motoboys
- Cadastro, atualização e listagem de motoboys
- Ativação e desativação
- Busca por placa

### 📍 Registros de KM
- Registro de KM de entrada do dia
- Controle de saída e retorno do almoço
- Finalização do registro com KM final
- Cálculo automático do total de KM rodado no dia
- Painel de registros **em aberto** do dia atual
- Histórico completo por motoboy
- Validações de negócio (KM não pode regredir, limite de 500km/dia, etc.)

### ⛽ Abastecimentos
- Cadastro de abastecimentos com litros e valor por litro
- Cálculo automático do valor total
- Associação com motoboy e posto
- Busca por motoboy, mês ou posto

### 📋 Postos
- Cadastro e consulta de postos de combustível

### 📄 Relatório PDF
- Geração de relatório mensal de KM por motoboy
- Download automático do PDF com dados do período

### 🖥️ Frontend Web
- Interface completa em HTML/CSS/JS puro
- Tela de login com autenticação JWT
- Cadastro de usuário no primeiro acesso
- Dashboard com métricas
- Gerenciamento completo via navegador, sem instalação

### 🔐 Segurança
- Autenticação com JWT
- Rotas protegidas
- Controle de acesso por roles (USER/ADMIN)

---

## 🐳 Docker

O projeto está containerizado com Docker e Docker Compose, com dois containers:
- **kmcontrol-app** — aplicação Spring Boot
- **kmcontrol-db** — PostgreSQL 16

```bash
docker-compose up -d
```

A imagem está publicada no Docker Hub:
```
docker pull matheusk27/kmcontrol-api:latest
```

---

## ☁️ Deploy

A aplicação está em produção na **AWS EC2** (São Paulo), rodando via Docker.

🌐 **URL pública:** [http://18.228.117.80:8080/kmcontrol.html](http://18.228.117.80:8080/kmcontrol.html)

---

## 📂 Estrutura do Projeto

```text
src/main/java
 ├── controller
 ├── servico
 ├── repositorio
 ├── entidades
 │   ├── dtoEntrada
 │   └── dtoSaida
 ├── infra
 └── seguranca

src/main/resources
 ├── db/migration        # Flyway migrations (V1 a V6)
 └── static              # Frontend (kmcontrol.html)
```

---

## ▶️ Como executar localmente

### 1️⃣ Clonar repositório
```bash
git clone https://github.com/MatheusK27/KmControl-Api.git
```

### 2️⃣ Configurar variáveis de ambiente
```env
DB_URL=jdbc:postgresql://localhost:5432/motoboy_db
DB_USERNAME=postgres
DB_PASSWORD=sua_senha
JWT_SECRET=sua_chave_jwt
```

### 3️⃣ Executar com Docker (recomendado)
```bash
docker-compose up -d
```

### 4️⃣ Ou executar localmente
```bash
./mvnw spring-boot:run
```

---

## 🌐 Endpoints principais

### 🔑 Autenticação
```http
POST /login
```
```json
{ "login": "usuario", "senha": "senha" }
```

### 🛵 Motoboys
```http
POST   /motoboys/cadastrar
GET    /motoboys
PUT    /motoboys/{id}
DELETE /motoboys/{id}
GET    /motoboys/placa?placa=ABC1D23
```

### 📍 Registros de KM
```http
POST /registro
GET  /registro/motoboy/{id}
PUT  /registro/finalizar/{id}
PUT  /registro/atualizar
GET  /registro/em-aberto/hoje
```

### ⛽ Abastecimentos
```http
POST /abastecimento
GET  /abastecimento/motoboy/{id}
GET  /abastecimento/mes?mes=7
GET  /abastecimento/posto/{id}
PUT  /abastecimento/{id}
DELETE /abastecimento/{id}
```

### 📄 Relatório PDF
```http
GET /relatorio/pdf?motoboyId=1&ano=2026&mes=7
```

---

## 📏 Regras de negócio

- Motoboy inativo não pode operar
- KM final não pode ser menor que KM inicial
- KM não pode regredir entre registros de dias diferentes
- Limite de 500km por dia
- Não é possível abrir dois registros no mesmo dia para o mesmo motoboy
- Registro em aberto em outro dia bloqueia novo cadastro
- Total de KM rodado é calculado e persistido automaticamente

---

## 🧪 Testes

Testes unitários implementados com **JUnit 5 + Mockito** cobrindo as regras de negócio da camada de serviço.

---

## 👨‍💻 Autor

**Matheus Klein**

🔗 GitHub: [https://github.com/MatheusK27](https://github.com/MatheusK27)
