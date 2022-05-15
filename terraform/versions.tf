terraform {
  required_providers {
    yandex = {
      source  = "yandex-cloud/yandex"
      version = "~> 0.74"
    }
  }

  backend "s3" {
    endpoint = "storage.yandexcloud.net"
    bucket   = "ecommerce-backend-dev"
    key      = "terraform.tfstate"
    region   = "ru-central1"

    skip_region_validation      = true
    skip_credentials_validation = true
  }
}
