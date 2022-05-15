provider "yandex" {
  zone = var.availability_zone
}

resource "yandex_vpc_network" "main" {
  name = "main-vpc"
}

resource "yandex_vpc_subnet" "public" {
  name = "public-subnet"

  network_id     = yandex_vpc_network.main.id
  zone           = var.availability_zone
  v4_cidr_blocks = [cidrsubnet(var.main_cidr_block, 8, 1)]
}

resource "yandex_vpc_subnet" "private" {
  name = "private-subnet"

  network_id     = yandex_vpc_network.main.id
  zone           = var.availability_zone
  v4_cidr_blocks = [cidrsubnet(var.main_cidr_block, 8, 2)]
  route_table_id = yandex_vpc_route_table.private.id
}

resource "yandex_vpc_route_table" "private" {
  name = "private-subnet-rt"

  network_id = yandex_vpc_network.main.id

  static_route {
    destination_prefix = "0.0.0.0/16"
    next_hop_address   = yandex_compute_instance.nat.network_interface.0.ip_address
  }
}

resource "yandex_compute_instance" "nat" {
  name        = "nat"
  platform_id = var.platform_id
  zone        = var.availability_zone

  resources {
    cores  = 2
    memory = 2
  }

  boot_disk {
    initialize_params {
      image_id = "fd85vbr6kin3r8ro2e95" # NAT instance
      size     = 8
      type     = "network-hdd"
    }
  }

  network_interface {
    subnet_id = yandex_vpc_subnet.public.id
    nat       = true
  }

  scheduling_policy {
    preemptible = var.preemptible
  }

  metadata = {
    ssh-keys = "ubuntu:${file("~/.ssh/id_rsa.pub")}"
  }
}

resource "yandex_compute_instance" "kmaster" {
  name        = "kmaster"
  platform_id = var.platform_id
  zone        = var.availability_zone

  resources {
    cores  = 2
    memory = 4
  }

  boot_disk {
    initialize_params {
      image_id = var.image_id
      size     = 8
      type     = "network-hdd"
    }
  }

  network_interface {
    subnet_id = yandex_vpc_subnet.private.id
  }

  scheduling_policy {
    preemptible = var.preemptible
  }

  metadata = {
    ssh-keys = "ubuntu:${file("~/.ssh/id_rsa.pub")}"
  }
}

resource "yandex_compute_instance" "kworker" {
  count = 2

  name        = "kworker-${count.index}"
  platform_id = var.platform_id
  zone        = var.availability_zone

  resources {
    cores  = 2
    memory = 4
  }

  boot_disk {
    initialize_params {
      image_id = var.image_id
      size     = 8
      type     = "network-hdd"
    }
  }

  network_interface {
    subnet_id = yandex_vpc_subnet.private.id
  }

  scheduling_policy {
    preemptible = var.preemptible
  }

  metadata = {
    ssh-keys = "ubuntu:${file("~/.ssh/id_rsa.pub")}"
  }
}
