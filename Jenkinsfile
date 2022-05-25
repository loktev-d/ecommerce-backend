pipeline {
    agent any 

    stages {
        stage('Terraform') {
            tools {
                terraform 'terraform'
            }

            stages {
                stage('Validate') { 
                    steps {
                        sh 'terraform validate'
                    }
                }

                stage('Apply') { 
                    steps {
                        sh 'terraform apply'
                    }
                }
            }
        }
    }
}