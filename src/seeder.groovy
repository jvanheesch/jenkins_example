pipelineJob('Generated') {
    definition {
        cpsScm {
            scm {
                git('https://github.com/jvanheesch/jenkins_example.git')
            }
            scriptPath("/src/com/github/jvanheesch/declarative/Jenskinsfile")
        }
    }
}