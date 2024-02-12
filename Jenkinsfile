pipeline {
    agent {
        node {
            label 'maven'
        }
    }

    environment {
        PATH = "/opt/apache-maven-3.9.6/bin:$PATH"
    }

    stages {
        stage("build") {
            steps {
                sh 'mvn clean deploy -Dmaven.test.skip=true'
            }
        } 

        stage(test) {
            steps{
                script {
                    echo "------------------- Unit Test started --------------------------------"
                    sh 'mvn surefire-report:report'
                    echo "------------------- Unit Test completed --------------------------------"
                }
            }
        }

        stage('SonarQube analysis') {
            environment {
                scannerHome = tool 'valaxy-sonar-scanner'
            }
            tools {
                jdk "jdk17"
            }
            steps {
                withSonarQubeEnv('valaxy-sonarqube-server') {
                    sh "${scannerHome}/bin/sonar-scanner"
                }
            }
        }
    }
}
