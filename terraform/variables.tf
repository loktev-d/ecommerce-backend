variable "aws_region" {
  default = "eu-central-1"
}

variable "availability_zone" {
  default = "eu-central-1a"
}

variable "main_cidr_block" {
  default = "10.0.0.0/16"
}

variable "instance_type" {
  default = "t2.micro"
}

variable "key_name" {
  default = "aws_kp"
}

variable "ami_name" {
  default = "ubuntu/images/hvm-ssd/ubuntu-jammy-22.04-amd64-server-*"
}
