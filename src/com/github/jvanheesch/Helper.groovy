package com.github.jvanheesch

class Helper {
//    static def mvn(script, args) {
//        script.sh "${script.tool 'maven'}/bin/mvn -s ${script.env.HOME}/jenkins.xml -o ${args}"
//    }

    def mvn(script, args) {
        script.steps.sh "${script.steps.tool 'Maven'}/bin/mvn -o ${args}"
    }

    static def publishTestResults(script) {
        script.junit '**/target/surefire-reports/*.xml'
    }

    static def checkoutProject(script) {
        script.git branch: '20180618.testing.jenkins', changelog: false, poll: false, url: 'git@github.com:jvanheesch/util.git'
    }

    static def test(script) {
        script.sh "echo 'test1'"
        script.echo "working"
    }
}