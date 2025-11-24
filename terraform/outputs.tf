output "alb_dns_name" {
  description = "URL pública do Load Balancer"
  value       = aws_lb.app_alb.dns_name
}

output "ecr_repository_url" {
  description = "URL do repositório ECR"
  value       = aws_ecr_repository.jwt_validator.repository_url
}

output "ecs_cluster_name" {
  description = "Nome do cluster ECS"
  value       = aws_ecs_cluster.cluster.name
}

output "ecs_service_name" {
  description = "Nome do service ECS"
  value       = aws_ecs_service.service.name
}