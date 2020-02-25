open class CopyDestination {
    var dest = "appBuilds"
}

class PipelinePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.task("defineCodeCheckBaseline") {
            description = "Create white list for code style check for all projects"
            group = "pipeline"

            project.allprojects.forEach {
                dependsOn("${it.path}:detektBaseline")
            }
            doLast {
                println("Successfully wrote baseline for projects")
            }
        }

        project.task("runCodeReview") {
            description = "Run detekt code style check for all projects"
            group = "pipeline"

            project.allprojects.forEach {
                dependsOn("${it.path}:detekt")
            }
            doLast {
                println("Successfully passed code style check")
            }
        }

        project.task("runTests") {
            description = "Run tests for all projects"
            group = "pipeline"

            project.allprojects.forEach {
                dependsOn("${it.path}:tests")
            }
            doLast {
                println("Successfully passed all tests")
            }
        }

        project.task("buildAndAssemble") {
            description = "Run assemble process"
            group = "pipeline"
            project.allprojects.forEach {
                if (it.name == "app") {
                    dependsOn("${it.path}:assemble")
                }
            }
            doLast {
                println("Successfully built and assembled.")

            }
        }

        val extension = project.extensions.create<CopyDestination>("pipeline")
        project.task("copyApk", Copy::class) {
            group = "pipeline"
            println("Copying to defined location...")
            from("app/build/outputs")
            into(extension.dest)
            println("Finished.")
        }
    }
}

apply<PipelinePlugin>()