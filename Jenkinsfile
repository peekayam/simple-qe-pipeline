pipeline {
  environment {
    registry = '168.62.187.166:8083/pwa-node-app:$BUILD_NUMBER'
    registryUrl = 'http://168.62.187.166:8083/'
    registryCredential = 'nexus'
    dockerImage = ''
    containerId = sh(script: 'docker ps -aqf "name=pwa-node-app"', returnStdout: true)
    allureId = sh(script: 'ps -ef | grep allure | grep -v grep |tr -s " "|cut -d " " -f2', returnStdout: true)
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
        sh 'cd pwa-app && npm run build'
      }
    }

    /* stage('Unit Test') {
      steps {
         sh 'cd pwa-app && npm test'
      }
    }*/
    stage('Sonar scan') {
      steps {
        sh 'cd pwa-app && /opt/sonar/bin/sonar-scanner -Dsonar.projectKey=pwa -Dsonar.sources=.'
      }
    }
    stage('Build Docker Image') {
      steps {
        script {
          dockerImage = docker.build(registry, "-f pwa-app/Dockerfile .")
        }
      }
    }
    stage('Cleanup running Container') {
      when {
        not {
          environment ignoreCase: true,
          name: 'containerId',
          value: ''
        }
      }
      steps {
        sh 'docker stop ${containerId}'
        sh 'docker rm ${containerId}'
      }
    }
    stage('Run Container') {
      steps {
        sh 'docker run --name=pwa-node-app -d -p 3000:3000 ' + registry
      }
    }
    stage('Cleanup') {
      steps {
        sh "cd JavaSeleniumBDD && mvn clean"
      }
    }
    stage('Stop Allure server') {
      when {
        not {
          environment ignoreCase: true,
          name: 'allureId',
          value: ''
        }
      }
      steps {
        sh 'kill -9 ${allureId}'
      }
    }
    stage('Functional Test') {
      steps {
        sh "cd JavaSeleniumBDD && chmod +x src/main/resources/drivers/chromedriverlinux85"
        sh "cd JavaSeleniumBDD && rm -rf allure-results/ |:"
        sh "cd JavaSeleniumBDD && mvn test -Dmaven.test.failure.ignore=true"
      }
    }
    stage('Run Allure server') {
      steps {
        sh "cd JavaSeleniumBDD  && JENKINS_NODE_COOKIE=dontkillme nohup /opt/allure/bin/allure serve allure-results --port 3030 &"
      }
    }
    stage('Performance Test') {
      steps {
        sh "cd JavaSeleniumBDD && rm -rf *.jtl|:"
        sh "cd JavaSeleniumBDD && /opt/jmeter/bin/jmeter -Jjmeter.save.saveservice.output_format=xml -n -t HTTPRequest.jmx -l HTTPRequest.jtl"
        sh "cd JavaSeleniumBDD && /opt/jmeter/bin/jmeter -Jjmeter.save.saveservice.output_format=xml -n -t SampleHTTPRequest2.jmx -l HTTPRequestLink.jtl"
        step([$class: 'ArtifactArchiver', artifacts: 'JavaSeleniumBDD/HTTPRequest.jtl'])
        step([$class: 'ArtifactArchiver', artifacts: 'JavaSeleniumBDD/HTTPRequestLink.jtl'])
        perfReport 'JavaSeleniumBDD/HTTPRequest.jtl'
        perfReport 'JavaSeleniumBDD/HTTPRequestLink.jtl'
      }
    }
    stage('Push Docker Image') {
      steps {
        script {
          docker.withRegistry(registryUrl, registryCredential) {
            dockerImage.push()
          }
        }
      }
    }
    stage('Remove Local Docker Image') {
      steps {
        sh 'docker rmi ' + registry
      }
    }
  }
}
