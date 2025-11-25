## 📄 IMPROVEMENTS.md

Opportunities for Improvement & Architectural Evolution

Este documento descreve melhorias planejadas e pontos que poderiam elevar ainda mais a qualidade, robustez e maturidade da aplicação e da infraestrutura.
Muitas destas decisões não foram implementadas por prioridade, escopo do desafio e tempo disponível, mas são melhorias totalmente conhecidas e planejadas.

---

## ✅ 1. Terraform Remote Backend (S3 + DynamoDB Lock)

Atualmente, o pipeline de CD executa o Terraform sem backend remoto, o que significa que cada execução:
- recria infraestrutura do zero,
- perde o state entre execuções,
- pode gerar múltiplos ALBs desnecessários,
- dificulta atualização incremental,
- torna o processo frágil.

# 🔧 Melhoria sugerida:

Criar um backend remoto:
```hcl
terraform {
  backend "s3" {
    bucket         = "jwt-validator-tfstate"
    key            = "infrastructure/terraform.tfstate"
    region         = "sa-east-1"
    dynamodb_table = "terraform-locks"
    encrypt        = true
  }
}
```

Benefícios:
- O Terraform sabe o que já existe.
- O CD se torna determinístico.
- O ALB deixa de ser recriado.
- Evita drift e falhas desnecessárias.
---

## ✅ 2. Aplicar Incrementos em vez de recriar Infraestrutura

O fluxo atual do CD cria toda a infraestrutura sempre que executado.
O ideal é que o CD faça apenas:
- build da imagem,
- push para o ECR,
- atualização da task definition,
- ECS rolling update.

Infraestrutura deve ser criada uma vez e mantida estaticamente via apply manual.
---

## ✅ 3. Módulos Terraform

A estrutura atual é funcional mas simples.

# 🔧 Melhoria sugerida:

Separar:
```
modules/
  ecs/
  alb/
  ecr/
```
Benefícios:
Reutilização, organização e mais clareza arquitetural.
---
## ✅ 4. HTTPS no Application Load Balancer

Hoje a aplicação está exposta com HTTP.

# 🔧 Melhoria sugerida:
- provisionar ACM (certificado SSL)
- atualizar o ALB para listener HTTPS (443)
- redirecionar HTTP → HTTPS

Isso melhora segurança, compliance e práticas de produção.
---

## ✅ 5. Segurança IAM (Least Privilege)

A role usada no GitHub Actions e a task execution role são permissivas por simplicidade do desafio.

🔧 Melhoria sugerida:
- reduzir escopo para ações estritamente necessárias (ECR, ECS, EC2 networking)
- remover wildcard "*"
- aplicar boundary policies
---

## ✅ 6. Observabilidade Avançada

Atualmente só o mínimo foi implementado:
- CloudWatch Logs

# 🔧 Futuras melhorias:
- Métricas customizadas refinadas
- Alarmes SNS (healthcheck 5xx, CPU, memory)
- Dashboard de observabilidade
- X-Ray tracing para latência interna
- Log forwarding para OpenSearch / Datadog
---

## ✨ Conclusão

O projeto atende todos os requisitos funcionais e técnicos do desafio.
As melhorias acima representam passos naturais para levar a solução a nível de produção, incluindo:
- robustez,
- governança,
- segurança,
- escalabilidade,
- eficiência de custos,
- maturidade DevOps/SRE.

