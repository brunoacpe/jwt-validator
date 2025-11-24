# JWT Validator API

![coverage](https://img.shields.io/badge/test%20coverage-100%25-brightgreen)

API responsável por validar um JWT enviado como `text/plain`, verificando:

- Se o token é bem formado
- Se contém **exatamente 3 claims**: `Name`, `Role`, `Seed`
- Se o valor de `Role` é válido (`Admin`, `Member`, `External`)
- Se `Name` possui apenas letras e até 256 caracteres
- Se `Seed` é um número primo
- Toda resposta da API deve ser apenas:  
  **`verdadeiro`** ou **`falso`**

---

## 🧪 Tecnologias utilizadas

- **Java 21**
- **Spring Boot 3**
- **Maven**
- **Jakarta Validation**
- **Auth0 JWT**
- **JUnit 5**
- **Jacoco (95%+)**
- **Docker**
- **Makefile**
- **SLF4J + Logstash Encoder (logs estruturados)**

---

# 📌 Como rodar o projeto

Abaixo estão todas as formas possíveis de executar a aplicação.

---

## ▶️ 1. Rodando com Maven

```bash
mvn clean install
mvn spring-boot:run
```

---
## 🐳 2. Rodando via Dockerfile

Build da imagem
```bash
docker build -t jwt-validator .
```

Executar o container
```bash
docker run -p 8080:8080 jwt-validator
```

A API estara em:
```
http://localhost:8080/api/v1/validate/jwt
```
---
## 🧰 3. Rodando via Makefile

Rodar build completo (Subir Docker, buildar app e expor na porta 8080)
```bash
make all
```
---

## 📂 Requisitos para rodar localmente

- Java 21+
- Maven 3.9+
- Docker latest
- Make qualquer versao
- Git latest

---

## 🔥 Endpoint da API

POST /api/v1/validate/jwt
- Content-Type: text/plain
- Body: uma string contendo o JWT

Exemplo de requisição:

```
POST http://localhost:8080/api/v1/validate/jwt
Content-Type: text/plain

<jwt-token-aqui>
```

Exemplo de resposta (válida):
```
"verdadeiro"
```

Exemplo de resposta (inválida):
```
"falso"
```
---

## 🧠 Como funciona a validação

A API executa a seguinte pipeline:
1.	Parser do JWT
- Decodifica o token
- Garante que possui exatamente 3 claims
- Garante que NÃO existe claim sobrando
- Garante que nenhuma claim exigida está faltando
- Converte a Role para Enum (Admin, Member, External)
- Constrói JwtPayloadDto

2.	Validação do DTO via Jakarta Validation
- Regex de Name
- Regex de Seed (somente dígitos)
- Tamanho máximo de Name
- Campo não pode ser nulo ou em branco

3.	Validação de Seed Primo
- Seed convertido para número
- Algoritmo otimizado verificando divisores até a raiz quadrada

4.	Conversão do resultado
- true → "verdadeiro"
- false → "falso"

5.	Qualquer erro → falso
- O desafio pede explicitamente essa regra.

---

## 📚 Descrição dos principais métodos

# JwtParser.parse(String jwt)

Responsável por:
- decodificar o JWT
- garantir claims válidas
- garantir ausência de claims extras
- validar nulls
- converter role para enum
- retornar JwtPayloadDto

# ValidatorUtil.validate(Object dto)

Usa Jakarta Validation para validar:
- formato
- regex
- tamanho
- campos obrigatórios

Lança BadRequestException no primeiro erro encontrado.

# SeedValidator.validate(String seed)

- Converte Seed para número e verifica se é primo.
- Lança BadRequestException se não for.

# JwtValidationService.validateJwt(String jwt)

Coordenador da lógica:
- parse 
- validações 
- retorno "verdadeiro"

---

## 📝 Premissas e Decisões Tomadas

Durante o desenvolvimento, algumas premissas foram assumidas para manter o projeto simples e coerente com o desafio.

✔ Premissa 1: Não usar arquitetura Hexagonal

O desafio não exige uma solução corporativa complexa.
Arquiteturas como Hexagonal, DDD e CQRS seriam overengineering para uma API com um único endpoint.

✔ Premissa 2: Manter o design simples (Controller → UseCase → Validators)

Clareza e testabilidade foram priorizadas em vez de estruturas sofisticadas.

✔ Premissa 3: Token inválido = falso

O desafio explicitamente exige que:

“Qualquer erro deve resultar em falso”

Por isso, todas as exceções do sistema são tratadas e retornam "falso" no Handler global.

✔ Premissa 4: Role convertida para Enum

Mesmo que o JWT traga "Admin" (capitalizado), o enum interno converte para RoleEnum.ADMIN.
Isso evita validação manual e mantém consistência.

✔ Premissa 5: Claims extras são inválidos

O desafio pede exatamente 3 claims.
Se chegar mais do que 3, ou uma claim desconhecida, o token é invalidado.

---

## 🧪 Testes

A aplicação possui:
- Testes de unidade (JUnit + Mockito)
- Testes de integração com RestAssured
- Cobertura Jacoco: 100%

---

## ✔ Conclusão

A solução foi construída priorizando:
- Simplicidade
- Clareza arquitetural
- Testabilidade
- Cobertura de código
- Logs estruturados
- Boas práticas de API
- Decisões arquiteturais justificáveis e realistas

Para qualquer expansão futura (infra com ECS/EKS, pipeline CD, Helm, etc), a estrutura já está preparada para receber novos módulos.