pipeline {
  environment {
    registry = '52.168.14.119:8083/tour-of-heroes:$BUILD_NUMBER'
    registryUrl = 'http://52.168.14.119:8083/'
    registryCredential = 'nexus'
    dockerImage = ''
    containerId = sh(script: 'docker ps -aqf "name=tour-of-heroes"', returnStdout: true)
    allureId = sh(script: 'ps -ef | grep allure | grep -v grep |tr -s " "|cut -d " " -f2', returnStdout: true)
	allureScreenShots = sh(script: 'date +"%Y-%m-%d"|tr -d "\n"', returnStdout: true)
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
        sh 'cd tour-of-heroes && chmod +x init_module.sh && ./init_module.sh'
        sh 'cd tour-of-heroes && npm run build'
      }
    }
    stage('Unit Test') {
      steps {
         sh 'cd tour-of-heroes && npm test -- --no-watch --no-progress --browsers=ChromeHeadlessCI'
      }
    }
    stage('Sonar scan') {
      steps {
        sh 'cd tour-of-heroes && /opt/sonar/bin/sonar-scanner -Dsonar.projectKey=tour-of-heroes -Dsonar.sources=.'
      }
    }
    stage('Build Docker Image') {
      steps {
        script {
          dockerImage = docker.build(registry, "-f tour-of-heroes/Dockerfile .")
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
        sh 'docker run --name=tour-of-heroes -d -p 3000:3000 ' + registry
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
		step([$class: 'ArtifactArchiver', artifacts: 'JavaSeleniumBDD/src/test/resources/screenshot/'+allureScreenShots+'/pwa/*'])
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
  }
}
