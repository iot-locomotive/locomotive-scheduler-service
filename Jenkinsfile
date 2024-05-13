pipeline {
    agent any

    tools {
        // Install the Maven version configured as "jenkins-maven" and add it to the path.
        maven "jenkins-maven"
    }

    stages {
        stage('Build Maven') {
            steps {
                // Get some code from a GitHub repository
                git branch: 'main', url: 'https://github.com/iot-locomotive/locomotive-scheduler-service.git'

                // Run Maven on a Unix agent.
                sh "mvn clean package"
            }
        }

        stage('Build Docker Image') {
            steps {
                // build docker image
                sh "docker build -t anandhias/locomotive-scheduler-service ."
            }
        }
        
        stage('Push Image to Docker Hub') {
            steps {
                // load docker hub credentials
                withCredentials([string(credentialsId: 'DOCKER_USER', variable: 'DOCKER_USER_VAR'), string(credentialsId: 'DOCKER_PASS', variable: 'DOCKER_PASS_VAR')]) {
                    // login to docker hub
                    sh "docker login -u ${DOCKER_USER_VAR} -p ${DOCKER_PASS_VAR}"
                }
                
                // push docker image to docker hub
                sh "docker push anandhias/locomotive-scheduler-service"
                
                // logout from docker hub
                sh "docker logout"
            }
        }
    }
}
