output "k8s_master_public_ip" {
  value = aws_instance.k8s_master.public_ip
}

output "k8s_master_private_ip" {
  value = aws_instance.k8s_master.private_ip
}

output "k8s_worker_1_private_ip" {
  value = aws_instance.k8s_worker_1.private_ip
}

output "k8s_worker_2_private_ip" {
  value = aws_instance.k8s_worker_2.private_ip
}
