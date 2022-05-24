plugins {
    kotlin("jvm") version "1.6.10"
    application // <2>
}

repositories {
    mavenCentral() // <3>
}

dependencies {
    implementation(project(":app-common"))
}

application {
    mainClass.set("com.faire.InnerApp") // <9>
}

gradlePlugin {
    plugins {
        create("simplePlugin") {
            id = "org.example.greeting"
            implementationClass = "org.example.GreetingPlugin"
        }
    }
}


abstract class CustomTask @Inject constructor(
    private val message: String,
    private val number: Int
) : DefaultTask() {
    @TaskAction
    fun mytask() {
        println("this is from task + $message  $number")
    }
}


tasks.register<CustomTask>("myTask", "hello", 42)