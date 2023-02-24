node {
  stage('SCM') {
    checkout scm
  }
  stage('SonarQube Analysis') {
    def mvn = tool 'maven-3.8.6';
    withSonarQubeEnv() {
      sh "${mvn}/bin/mvn clean verify sonar:sonar -Dsonar.projectKey=odsapi"
    }
  }
}

pipeline {

    agent any

    stages {

        stage('Build') {
            steps {
                echo 'Build'
                sh "mvn --batch-mode package"
            }
        }

        stage('Archive Unit Tests Results') {
            steps {
                echo 'Archive Unit Test Results'
               step([$class: 'JUnitResultArchiver', testResults: 'target/surefire-reports/TEST-*.xml'])
            }
        }

        stage('Publish Unit Test results report') {
            steps {
                echo 'Report'
                publishHTML([allowMissing: false, alwaysLinkToLastBuild: true, keepAll: false, reportDir: 'target/sonar/', reportFiles: 'index.html', reportName: 'jacaco report', reportTitles: ''])

             }
        }

        stage('Dependency check') {
                    steps {
                        sh "mvn --batch-mode dependency-check:check"
                    }
                    post {
                        always {
                            publishHTML(target:[
                                allowMissing: true,
                                alwaysLinkToLastBuild: true,
                                keepAll: true,
                                reportDir: 'target',
                                reportFiles: 'dependency-check-report.html',
                                reportName: "OWASP Dependency Check Report"
                            ])
                        }
                    }
                }
        stage('Deploy') {
            steps {
                echo 'Deploy'
            }
        }
    }
}