terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 4.13"
    }
  }

  backend "s3" {
    bucket = "ecommerce_backend"
    key    = "dev/terraform.tfstate"
    region = var.aws_region
  }
}
