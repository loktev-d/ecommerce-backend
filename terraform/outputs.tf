output "nat_public_ip" {
  value = yandex_compute_instance.nat.network_interface.0.nat_ip_address
}

output "nat_private_ip" {
  value = yandex_compute_instance.nat.network_interface.0.ip_address
}

output "kmaster_private_ip" {
  value = yandex_compute_instance.kmaster.network_interface.0.ip_address
}

output "kworker_storage_private_ip" {
  value = yandex_compute_instance.kworker_storage.network_interface.0.ip_address
}

output "kworkers_private_ips" {
  value = yandex_compute_instance.kworker[*].network_interface.0.ip_address
}
