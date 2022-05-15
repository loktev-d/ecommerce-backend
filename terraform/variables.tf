variable "region" {
  default = "ru-central1"
}

variable "availability_zone" {
  default = "ru-central1-a"
}

variable "main_cidr_block" {
  default = "10.0.0.0/16"
}

variable "image_id" {
  default = "fd86t95gnivk955ulbq8"
}

variable "platform_id" {
  default = "standard-v1"
}

variable "preemptible" {
  default = true
}
