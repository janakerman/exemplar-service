
resource "aws_cloudwatch_log_group" "task_logs" {
  name_prefix = "example-service"

  tags = {
    Environment = "production"
    Application = "serviceA"
  }
}