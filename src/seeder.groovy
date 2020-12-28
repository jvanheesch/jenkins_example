pipelineJob('Generated') {
    definition {
        cpsScm {
            scm {
                // https://stackoverflow.com/questions/44884121/jenkins-disable-tag-every-build-in-dsl
                git {
                    remote { url('https://github.com/jvanheesch/jenkins_example.git') }
                    extensions {}
                }
            }
            scriptPath("src/com/github/jvanheesch/release1/alltests/Jenkinsfile")
        }
    }
}
