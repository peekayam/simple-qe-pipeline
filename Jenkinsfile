pipeline {
  environment{
    registry = "mazuma5/pipeline-project"
    registryCredential = 'Dockerhub'
    dockerImage = ''
    containerId = sh(script: 'docker ps -aqf "name=pwa-node-app"', returnStdout:true)
  }
  agent any
  tools {
    nodejs "node"
  }
    
 stages {
  stage('Cloning Git') {
      steps {
        sh 'cd ..'
        git 'git@github.com:mazuma5/JavaSeleniumBDD.git'
      }
    }
    
        
    stage('Build') {
      steps {
        sh 'npm install --save-dev @angular-devkit/build-angular'
        sh 'npm install --save-dev http-server'
        sh 'npm install @angular/cli'
        sh 'ng build --prod'
      }
    }
     
   /*stage('Test') {
      steps {
         sh 'npm test'
      }
    }*/
    stage('Sonar scan'){
      steps{
        sh '/app/sonar-scanner/bin/sonar-scanner -Dsonar.projectKey=pwa -Dsonar.sources=.'
      }
    }
    stage('Building Image'){
      steps{
        script{
          dockerImage = docker.build registry + ":$BUILD_NUMBER"
        }
      }
    }
    
    stage('Push Image'){
      steps{
        script{
          docker.withRegistry('',registryCredential) {
            dockerImage.push()
          }
        }
      }
    }
    
    stage('Cleanup'){
      when{
        not {environment ignoreCase:true, name:'containerId', value:''}
      }
      steps {
        sh 'docker stop ${containerId}'
        sh 'docker rm ${containerId}'
      }
    }
    stage('Run Container'){
      steps{
        sh 'docker run --name=pwa-node-app -d -p 3000:8080 $registry:$BUILD_NUMBER &'
      }
    }    
  }
}
