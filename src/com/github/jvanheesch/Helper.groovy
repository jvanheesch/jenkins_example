package com.github.jvanheesch

class Helper {
    static def mvn(script, args) {
        script.sh "${script.tool 'Maven'}/bin/mvn -s ${script.env.HOME}/jenkins.xml -o ${args}"
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