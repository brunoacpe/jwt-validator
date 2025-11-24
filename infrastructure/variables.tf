variable "aws_region" {
  description = "AWS region"
  type        = string
  default     = "sa-east-1"
}

variable "project_name" {
  description = "Projeto que valida um token JWT."
  type        = string
  default     = "jwt-validator"
}

variable "container_port" {
  description = "Porta exposta pelo container"
  type        = number
  default     = 8080
}

variable "desired_count" {
  description = "Numero de tasks Fargate desejadas"
  type        = number
  default     = 1
}

variable "task_cpu" {
  description = "CPU da task Fargate (em unidades da AWS)"
  type        = number
  default     = 256
}

variable "task_memory" {
  description = "Memoria da task Fargate (MB)"
  type        = number
  default     = 512
}