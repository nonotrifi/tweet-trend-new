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
            steps  {
                sh 'mvn clean deploy'
            }
        }
    } 

        // stage('SonarQube Analysis') {
        //     environment {
        //         scannerHome = tool 'valaxy-sonar-scanner';
        //     }
        //     steps {
        //         withSonarQubeEnv('valaxy-sonarqube-server') {
        //         sh "${ScannerHome}/bin/sonar-scanner"
        //     }
        //     }
            
        // }
}