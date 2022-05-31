import javax.inject.Inject
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.file.FileCollection
import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Classpath
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.property
import org.gradle.process.ExecOperations

abstract class NotifyAfterTask : DefaultTask() {

    @get:Inject
    protected abstract val execOperations: ExecOperations

    @get:Inject
    abstract val objects: ObjectFactory

    @get:Classpath
    val execClasspath: ConfigurableFileCollection = objects.fileCollection()

    @get:Input
    val mainClassFQN: Property<String> = objects.property<String>()


    @Internal
    val toolProject: Property<Project> = objects.property<Project>()
        .convention(
            project.findProject(":app") ?: error("Could not locate tools project: :app")
        )

    @get:Classpath
    val toolClasspath: FileCollection = project.files(
        toolProject.map { project ->
            val classpathsToAdd = project.sourceSets["main"].runtimeClasspath
            logger.debug("classpath += /* tool.main.runtime */ ${classpathsToAdd.files}")

            classpathsToAdd
        }
    )

    init {
        dependsOn(toolProject.map { it.tasks.named("classes") })
    }

    @TaskAction
    fun execute() {
        val exec = execOperations.javaexec {
            mainClass.set(mainClassFQN)

            // These use resolved dependencies in the gradle chain, thus needs to be done just before
            // we run the task
            classpath = execClasspath + toolClasspath
            args = listOf(
                "command.get()",
                "-j",
                "settingJsonOutputFile.get().toString()",
                "-s",
                "settingHashOutputFile.get().toString()",
            )

        }

        exec.assertNormalExitValue()
    }
}

