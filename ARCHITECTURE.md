# 🏗️ Arquitetura da Solução

Este documento descreve a arquitetura escolhida para hospedar a aplicação **JWT Validator API**, além das decisões técnicas adotadas, justificativas e visão de alto nível de como os componentes interagem na infraestrutura AWS.

---

# 📌 Visão Geral da Arquitetura

A aplicação será executada em uma infraestrutura **100% gerenciada pela AWS**, baseada em:

- **ECR (Elastic Container Registry)** para armazenar a imagem Docker da aplicação
- **ECS Fargate** para executar a aplicação sem a necessidade de gerenciar servidores
- **ALB (Application Load Balancer)** para expor o serviço na internet de forma segura, escalável e padronizada
- **GitHub Actions** para CI/CD
- **OpenTofu/Terraform** para provisionamento da infraestrutura

Essa arquitetura é otimizada para aplicações **stateless**, como este validador de JWT, garantindo escalabilidade linear, simplicidade operacional e baixo custo.

---
## 🧩 Componentes da Arquitetura

1. ECR – Elastic Container Registry

Responsável por armazenar a imagem Docker gerada no pipeline CI.
- Versionamento automático de imagens
- Integração nativa com ECS
- Autenticação IAM segura
---
2. ECS Fargate

A aplicação é executada em um serviço ECS configurado com:
- Fargate (serverless containers)
- Task Definition contendo:
  - Image ECR
  - CPU e memória configuradas
  - Porta exposta (8080)
  - Health check (/actuator/health)
  - Service ECS gerenciando:
  - Desired count
  - Auto-recover
  - Integração com ALB

Por que Fargate?
- Não há servidores para gerenciar (No EC2)
- Custo proporcional ao uso
- Segurança gerenciada
- Ideal para workloads pequenos ou médios
- Reduz complexidade em comparação com EKS
---

3. Application Load Balancer (ALB)

O ALB é o ponto de entrada da API.

Funções:
- Recebe tráfego HTTP/HTTPS
- Encaminha para o ECS Fargate
- Health checks
- Integração automática com ECS Target Groups
---

4. Rede (VPC + Subnets)
   - VPC exclusiva
   - Subnets privadas para o ECS Fargate
   - Subnets públicas para o ALB
   - Security Groups com regras restritas:
   - ALB acessível publicamente (80/443)
   - ECS só recebe tráfego vindo do ALB

---

5. CI/CD – GitHub Actions

Pipeline dividido em duas etapas:

CI (Integração Contínua)
- Executa testes (JUnit + Jacoco)
- Avalia cobertura mínima (ex: 95%)
- Build da aplicação via Maven
- Build da imagem Docker

CD (Entrega Contínua)
- Push da imagem para o ECR
- Atualização do ECS Service com a nova imagem
- Rollout automático do novo container
- Health-check por ALB antes de colocar em produção
---

## 🎯 Motivação da Escolha Arquitetural

✔ Simplicidade

O escopo do desafio consiste em uma única API.
Rodar isso em Kubernetes (EKS + Helm + Nginx + IRSA etc.) seria overengineering.

✔ Custo

Fargate cobra apenas CPU/memória em execução, não há custos por cluster como no EKS.

✔ Velocidade de entrega

Provisionamento ágil e documentação mais enxuta — ideal para desafios técnicos.

✔ Segurança nativa

IAM + ECS Task Role + Security Groups bem definidos.

✔ Aderência ao desafio

A arquitetura respeita:
•	Containerização
•	Deploy automatizado
•	Exposição em cloud pública
•	Uso de Terraform
•	Boas práticas AWS
---

## 🔒 Segurança
- O container roda em subnets privadas
- Somente o ALB é público
- A Task Role limita acesso de permissões
- Comunicação interna protegida por SG → SG
- TLS pode ser habilitado facilmente via ACM
---

🔄 Fluxo de Deploy
1.	Desenvolvedor faz push para main
2.	GitHub Actions roda testes e valida cobertura Jacoco
3.	Builda e envia imagem Docker ao ECR
4.	Atualiza ECS Service
5.	ECS inicia nova Task com a nova imagem
6.	ALB faz health-check
7.	Se tudo OK → tráfego é roteado para a nova versão
---
## 📝 Considerações Finais

A arquitetura proposta equilibra:
- Simplicidade
- Baixo custo
- Segurança
- Escalabilidade
- Robustez
- Clareza para o avaliador

Ela atende perfeitamente o desafio sem cair em complexidade desnecessária.
Caso a aplicação cresça, é possível expandir facilmente para:
- Autoscaling por CPU/mem
- Fargate Spot
- Ou até migração para EKS, se fizer sentido futuramente
