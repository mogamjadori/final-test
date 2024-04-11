pipeline {
    agent any
    
    environment {
        strDockTag = "${TODAY}_${BUILD_ID}"
        strDockImg = "choisieun/gym-fit:${strDockTag}"
    }
    
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/mogamjadori/final-test.git'
            }
        }
      
        stage('Build') {
            steps {
                sh "./gradlew build -x test"
            }
            post {
                success {
                    archiveArtifacts "build/libs/*.jar"
                }
            }
        }
        
        stage('Unit Test') {
            steps {
                sh "./gradlew test"
            }
            post {
                always {
                    junit "**/build/test-results/test/*.xml"
                }
            }
        }
        
        stage('Build Docker Image') {
            steps {
                script {
                    oDockImg = docker.build(strDockImg, "--build-arg VERSION=${strDockTag} -f Dockerfile .")
                }
            }
        }
        
        stage('Push Docker Image') {
            steps {
                script {
                    oDockImg = docker.withRegistry('', 'dockerhub') {
                        oDockImg.push()
                    }
                }
            }
        }
    }
}
