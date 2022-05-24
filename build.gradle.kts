plugins {
    `java-library`
    kotlin("jvm") version "1.6.10"// has implementation
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(projects.app)
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
}

apply<NotifyAfterPlugin>()


val JVM_TARGET = JavaVersion.VERSION_11


abstract class CustomTaskInRoot @Inject constructor(
    private val message: String,
    private val number: Int
) : DefaultTask(){
    @TaskAction
    fun mytask(){
        val buildDir = project.layout.buildDirectory
        println("buildDir = ${buildDir.get()}")
        println("this is from task  $message  $number")
    }
}


tasks.register<CustomTaskInRoot>("myTaskInRoot", "hello from root", 42)
