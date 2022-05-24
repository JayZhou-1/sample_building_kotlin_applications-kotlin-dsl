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



plugins {
    `java-library`
    kotlin("jvm") version "1.6.10"// has implementation
//    kotlin("plugin.serialization") version "1.6.10"
}

repositories {
    jcenter {
        content {
            includeGroup("org.jetbrains.kotlinx")
        }
    }
    mavenCentral()
    maven(url = "https://oss.sonatype.org/content/repositories/snapshots/")

}

dependencies {
//    implementation(kotlin("stdlib"))
//    implementation(kotlin("stdlib-jdk8"))
    implementation(projects.app)
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
}

apply<NotifyAfterPlugin>()

//allprojects{
//    repositories {
//        mavenCentral()
//        jcenter()
//    }
//    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
//        kotlinOptions {
//            jvmTarget = JVM_TARGET.toString()
//            incremental = true
//            suppressWarnings = true
//            freeCompilerArgs = listOf("-Xjvm-default=enable", "-Xinline-classes")
//        }
//    }
//}
//
//
//subprojects {
//
//    repositories {
//        mavenCentral()
//        jcenter()
//    }
//}
val JVM_TARGET = JavaVersion.VERSION_11
