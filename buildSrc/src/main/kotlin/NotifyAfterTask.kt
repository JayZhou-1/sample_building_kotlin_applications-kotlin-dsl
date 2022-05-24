import javax.inject.Inject
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.model.ObjectFactory
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.Classpath
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.TaskAction
import org.gradle.process.ExecOperations

abstract class NotifyAfterTask : DefaultTask() {

    @get:Inject
    protected abstract val execOperations: ExecOperations

    @get:Inject
    protected abstract val objects: ObjectFactory

    @get:Classpath
    val execClasspath: ConfigurableFileCollection = objects.fileCollection()

    @TaskAction
    fun execute() {
        val exec = execOperations.javaexec {
            mainClass.set("com.faire.CliAppEntry")

            // These use resolved dependencies in the gradle chain, thus needs to be done just before
            // we run the task
            classpath = execClasspath
            val commonArgs = listOf(
                "command.get()",
                "-j",
                "settingJsonOutputFile.get().toString()",
                "-s",
                "settingHashOutputFile.get().toString()",
            )

            args = commonArgs
        }

        exec.assertNormalExitValue()
    }
}

