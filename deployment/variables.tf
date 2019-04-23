variable "aws_region" {
  description = "AWS region to create resources in"
  default     = ""
}

variable "az_count" {
  default     = ""
}

variable "app_port" {
  default     = ""
}

variable "fargate_cpu" {
  default     = ""
}

variable "fargate_memory" {
  default     = ""
}

variable "app_image" {
  default     = ""
}

variable "app_count" {
  default     = ""
}

variable "route53_zone_id" {
  default     = ""
}

variable "route53_record_name" {
  default     = ""
}
