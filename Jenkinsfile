pipeline {
    environment {
        registry = "sample/department"
        registryCredential = 'dockerhub_id'
        dockerImage = ''
    }
    
    agent {
        docker {
            image 'maven:3-alpine'
            args '-v /root/.m2:/root/.m2'
        }
    }
    stages {

        stage('Clone repository') {
            steps {
            /* Clone our repository */
                checkout scm
            }    
        }

        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Validate the Jar Version') {
            steps {
                sh './ci-build/deliver.sh'
            }
            post {
                always {
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
            }
        }
    }
}

stage('Build image') {
    node {
        withEnv([
            'SERVICE=organization',
            'TAG=1.1'
        ]){
        /* Build the docker image */
            sh 'echo "clear <none docker images>"'
            sh "./ci-build/clear-images.sh"
            sh "docker build --no-cache -t ${SERVICE}:${TAG} ."
        }
    }
}

stage('Deploy to k8s') {
    node {
        withEnv([
            'SERVICE=organization',
            'NAMESPACE=default',
            'TAG=1.1'
        ]){
            /* Apply all manifest files */
            sh "pwd"
            sh "cat /root/.kube/ca.crt"
            sh "./deployment/deploy.sh"
        }
    }    
}