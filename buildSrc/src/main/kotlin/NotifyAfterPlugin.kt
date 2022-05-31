import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.property
import org.gradle.kotlin.dsl.register


class NotifyAfterPlugin : Plugin<Project> {
    lateinit var notifyAfterTask: TaskProvider<NotifyAfterTask>
        private set

    override fun apply(project: Project) {

        val javaExtension = project.extensions.getByType(JavaPluginExtension::class.java)
        val mainSourceSet = javaExtension.sourceSets["main"]
        val runTimeClassPath = mainSourceSet.runtimeClasspath
        val outputClassDir = mainSourceSet.output.classesDirs
        val compileClassPath = mainSourceSet.compileClasspath

        println("project = ${project.rootProject.name}")

        notifyAfterTask = project.tasks.register<NotifyAfterTask>("notifyAfter") {
            val stringProvider = objects.property<String>().convention("com.faire.CliAppEntry")
            mainClassFQN.set(stringProvider)
            execClasspath.setFrom(runTimeClassPath)
        }

        val slackTask = project.tasks.register<NotifyAfterTask>("slack") {
            mainClassFQN.set("com.faire.NotificationCliEntry")
        }

        project.task("jayzhou") {
            doLast {
                val projects = project.allprojects
                println("==========projects==========")
                projects.forEach {
                    println(it.displayName)
//                    println("project.buildDir.absolutePath = ${it.buildDir.absolutePath}")
//                    println("it.findProject(\":app:common\") = ${it.findProject(":app-common")}")
                    try {
                        val javaExtension = it.extensions.getByType(JavaPluginExtension::class.java)
                        val mainSourceSet = javaExtension.sourceSets["main"]
                        val runTimeClassPath = mainSourceSet.runtimeClasspath.asPath
                        val compileClassPath = mainSourceSet.compileClasspath.asPath
                        println("runTimeClassPath = ${runTimeClassPath}")
                        println()
                        println("compileClassPath = ${compileClassPath}")
                    } catch (e: Exception) {
                        println("could not get JavaExtension")
                    }
                    println()
                    println()
                }
            }
        }

    }
}

internal val Project.sourceSets: SourceSetContainer
    get() {
        val javaExtension = project.extensions.getByType(JavaPluginExtension::class.java)
        return javaExtension.sourceSets
    }