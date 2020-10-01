pipeline {
  environment{
    registry = "peekayam/pipeline-project"
    registryCredential = 'Dockerhub'
    dockerImage = ''
    containerId = sh(script: 'docker ps -aqf "name=pwa-node-app"', returnStdout:true)
  }
  agent any
  tools {
    nodejs "node"
    maven "Maven"
    jdk "JDK"
  }
    
 stages {
    stage('Build App') {
      steps {
        sh 'cd pwa-app && chmod +x init_module.sh && ./init_module.sh'
        //sh 'cd pwa-app && npm install'
        sh 'cd pwa-app && npm run build'
      }
    }
     
  /* stage('Unit Test') {
      steps {
         sh 'cd pwa-app && npm test'
      }
    }*/
   /*stage('Sonar scan'){
      steps{
        sh 'cd pwa-app && /app/sonar-scanner/bin/sonar-scanner -Dsonar.projectKey=pwa -Dsonar.sources=.'
      }
    }*/
    stage('Building Image'){
      steps{
        script{
          //dockerImage = docker.build registry + ":$BUILD_NUMBER"
          sh 'docker build -t mazuma5/pipeline-project:$BUILD_NUMBER -f pwa-app/Dockerfile .'
        }
      }
    }
    
    /*stage('Push Image'){
      steps{
        script{
          docker.withRegistry('',registryCredential) {
            //dockerImage.push()
            sh 'docker tag mazuma5/pipeline-project:$BUILD_NUMBER mazuma5/pipeline-project:$BUILD_NUMBER'
            sh 'docker push mazuma5/pipeline-project:$BUILD_NUMBER'
          }
        }
      }
    }*/
    
    stage('Cleanup running Container'){
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
        sh 'docker run --name=pwa-node-app -d -p 3000:3000 mazuma5/pipeline-project:$BUILD_NUMBER &'
      }
    } 
    stage('Cleanup'){
      steps{
        sh "cd JavaSeleniumBDD && mvn clean"
      }
    }
    stage('Functional Test') {
        steps {
          sh "cd JavaSeleniumBDD && chmod +x src/main/resources/drivers/chromedriverlinux85"
          sh "cd JavaSeleniumBDD && rm -rf allure-results/ |:"
          sh "cd JavaSeleniumBDD && mvn test -Dmaven.test.failure.ignore=true"
        }
      }  
    stage('Run Allure') {
      steps {
        sh "cd JavaSeleniumBDD && JENKINS_NODE_COOKIE=dontkillme nohup /opt/allure/bin/allure serve allure-results --port 3030 &"
      }
    } 
   /* stage('Performance Test') {
      steps {
        sh "cd JavaSeleniumBDD && rm -rf *.jtl|:"
        sh "cd JavaSeleniumBDD && /app/apache-jmeter-5.1/bin/jmeter -Jjmeter.save.saveservice.output_format=xml -n -t HTTPRequest.jmx -l HTTPRequest.jtl"
        sh "cd JavaSeleniumBDD && /app/apache-jmeter-5.1/bin/jmeter -Jjmeter.save.saveservice.output_format=xml -n -t SampleHTTPRequest2.jmx -l HTTPRequestLink.jtl"
        step([$class: 'ArtifactArchiver', artifacts: 'JavaSeleniumBDD/HTTPRequest.jtl'])
        step([$class: 'ArtifactArchiver', artifacts: 'JavaSeleniumBDD/HTTPRequestLink.jtl'])
      }
    }*/
  }
}
