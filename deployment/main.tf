provider "aws" {
  region = "${var.aws_region}"
}

# Fetch AZs in the current region
data "aws_availability_zones" "available" {}
