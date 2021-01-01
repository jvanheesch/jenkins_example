# Description
This project is part of an old jenkins example.
# Dependencies
- https://github.com/jvanheesch/util/tree/20180915.test.jenkins
- https://github.com/jvanheesch/-jenkins_example_utilities
- slack (sandboxjoris)
# Setup
- Create a jenkins instance
- Configure -jenkins_example_utilities as implicit shared library
- Configure this repo as shared library 'test-joris'
- Configure a maven installation with name 'Maven 3.6.3'
- Create a seed job (freestyle, clone this repo, process job dsl on filesystem)
- Run -> failure
- Approve the script
- Run again -> success
# Jenkins setup using docker & jcasc:
`plugins.txt`
```
configuration-as-code:1.46
git:4.5.0
job-dsl:1.77
workflow-job:2.40
workflow-aggregator:2.6
workflow-multibranch:2.22
pipeline-utility-steps:2.6.1
# contains timestamps step
timestamper:1.11.8
slack:2.45
```

`Dockerfile`
```
FROM jenkins/jenkins:2.263.1-lts
COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN jenkins-plugin-cli -f /usr/share/jenkins/ref/plugins.txt
```

`jcasc.yml`
```
jenkins:
  securityRealm:
    local:
      allowsSignup: false
      users:
        - id: "admin"
          password: "admin"
  authorizationStrategy: loggedInUsersCanDoAnything
tool:
  maven:
    installations:
      - name: "Maven 3.6.3"
        properties:
          - installSource:
              installers:
                - maven:
                    id: "3.6.3"
credentials:
  system:
    domainCredentials:
      - credentials:
          - string:
              scope: GLOBAL
              id: slack-token
              secret: TODO // no secret in public git repo
              description: Slack token
unclassified:
  slackNotifier:
    teamDomain: sandboxjoris
    tokenCredentialId: slack-token
  globalLibraries:
    libraries:
      - name: "test-joris"
        defaultVersion: "master"
        retriever:
          modernSCM:
            scm:
              git:
                remote: "https://github.com/jvanheesch/jenkins_example"
      - name: "utilities_implicit"
        defaultVersion: "master"
        implicit: true
        retriever:
          modernSCM:
            scm:
              git:
                remote: "https://github.com/jvanheesch/-jenkins_example_utilities"
jobs:
  - script: |
      freeStyleJob('example') {
          scm {
              git {
                  remote { url('https://github.com/jvanheesch/jenkins_example/') }
                  extensions {}
              }
          }
          steps {
              dsl {
                  external('src/seeder.groovy')
              }
          }
      }
```

`docker-compose.yml`
```
version: "3.9"

services:
  jenkins:
    image: TODO // replace with name of img
    ports:
      - "10080:8080"
    environment:
      CASC_JENKINS_CONFIG: "/usr/share/jenkins/ref/casc_configs"
      JAVA_OPTS: -Djenkins.install.runSetupWizard=false
    volumes:
      - home:/var/jenkins_home
      - ./casc_configs:/usr/share/jenkins/ref/casc_configs
volumes:
  home:
```
# Remarks
Only the alltests Jenkinsfile is still relevant.
