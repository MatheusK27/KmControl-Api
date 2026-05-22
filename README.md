
# 🚀 KmControl API

API REST desenvolvida para controle de operações de motoboys, abastecimentos e registros de quilometragem.

O sistema foi criado para resolver um problema real de controle manual de:
- 🛵 KM inicial e final das motos
- ⛽ Abastecimentos
- 📊 Consumo de combustível
- 📋 Controle operacional de motoboys

---

# 🛠️ Tecnologias utilizadas

- ☕ Java 21
- 🌱 Spring Boot
- 🔐 Spring Security
- 🎟️ JWT Authentication
- 🗄️ Spring Data JPA
- 🐘 PostgreSQL
- 📦 Maven
- ⚙️ Hibernate
- ✅ Bean Validation

---

# ✨ Funcionalidades

## 🛵 Motoboys
- Cadastro de motoboys
- Atualização de dados
- Ativação/desativação
- Listagem de motoboys

## 📍 Registros de KM
- Registro de KM inicial/final
- Controle de utilização da moto
- Histórico de registros

## ⛽ Abastecimentos
- Cadastro de abastecimentos
- Associação com motoboy e posto
- Cálculo de consumo
- Histórico de abastecimentos

## 🔐 Segurança
- Autenticação com JWT
- Rotas protegidas
- Controle de acesso

---

# 📂 Estrutura do Projeto

```text
src/main/java
 ├── controller
 ├── service
 ├── repository
 ├── domain
 ├── dto
 ├── infra
 └── security
```

---

# ▶️ Como executar o projeto

## 1️⃣ Clonar repositório

```bash
git clone https://github.com/MatheusK27/KmControl-Api.git
```

---

## 2️⃣ Configurar variáveis de ambiente

```env
DB_URL=jdbc:postgresql://localhost:5432/motoboy_db
DB_USERNAME=postgres
DB_PASSWORD=sua_senha
JWT_SECRET=sua_chave_jwt
```

---

## 3️⃣ Executar aplicação

```bash
./mvnw spring-boot:run
```

---

# 🌐 Endpoints principais

## 🔑 Autenticação

### Login

```http
POST /login
```

Body:

```json
{
  "login": "admin",
  "senha": "123456"
}
```

---

# 🛵 Motoboys

### ➕ Cadastrar motoboy

```http
POST /motoboys
```

### 📋 Listar motoboys

```http
GET /motoboys
```

### ✏️ Atualizar motoboy

```http
PUT /motoboys/{id}
```

---

# ⛽ Abastecimentos

### ➕ Registrar abastecimento

```http
POST /abastecimento
```

### 📜 Buscar abastecimentos por motoboy

```http
GET /abastecimento/motoboy/{id}
```

---

# 📏 Regras de negócio

- ❌ Motoboy inativo não pode operar
- 🚫 KM final não pode ser menor que KM inicial
- ✅ Abastecimentos devem possuir motoboy válido
- 📚 O sistema mantém histórico operacional das motos

---
# 🚧 PROJETO EM DESENVIMENTO
# 🚧 Melhorias futuras

- 📊 Dashboard web
- 📈 Relatórios de consumo
- ☁️ Deploy em nuvem
- 🐳 Docker
- 🧪 Testes automatizados
- 💰 Controle financeiro
- 📎 Upload de comprovantes

---

# 👨‍💻 Autor

Matheus Klein

🔗 GitHub:
https://github.com/MatheusK27
