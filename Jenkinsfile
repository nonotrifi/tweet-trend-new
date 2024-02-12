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

        // stage('SonarCloud analysis') {
        //     environment {
        //         scannerHome = tool 'valaxy-sonar-scanner' // the name you have given the Sonar Scanner (in Global Tool Configuration)
        //     }
        //     tools {
        //         jdk "jdk17" // the name you have given the JDK installation in Global Tool Configuration
        //     }
        //     steps {
        //         withSonarQubeEnv(installationName: 'valaxy-sonarqube-server') {
        //             sh "${scannerHome}/bin/sonar-scanner -X"
        //         }
        //     }
        // }
        stage('SonarQube analysis') {
    environment {
      scannerHome = tool 'valaxy-sonar-scanner'
    }
    tools {
        jdk "jdk17"
    }
    steps{
    withSonarQubeEnv('valaxy-sonarqube-server') { // If you have configured more than one global server connection, you can specify its name
      sh "${scannerHome}/bin/sonar-scanner"
    }
    }
  }
    }
}
