pipeline {
    agent any
    stages {
        stage('Pull') {
            steps {
                checkout([
                    $class: 'GitSCM', 
                    branches: [[name: '*/master']], 
                    doGenerateSubmoduleConfigurations: false, 
                    extensions: [[$class: 'CleanCheckout']], 
                    submoduleCfg: [], 
                    userRemoteConfigs: [
                        [
                            credentialsId: 'gitAccess', 
                            url: 'https://github.com/Nikolaiko/chesstask'
                        ]
                    ]
                ])
            }
        }
        stage('codeReview') {
            steps {
                sh './gradlew runCodeReview'
            }
        }
        stage('runTests') {
            steps {
                sh './gradlew runTests'
            }
        }
        stage('assembleAndDeploy') {
            steps {
                sh './gradlew appCenterAssembleAndUploadProdRelease'
            }
        }
        stage('saveToArtifactory') {
            steps {
                sh './gradlew artifactoryPublish'
            }
        }
    }
}