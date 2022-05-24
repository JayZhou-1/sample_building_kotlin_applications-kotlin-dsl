plugins {
    kotlin("jvm") version "1.6.10"
    `java-gradle-plugin`

    application // <2>
}

repositories {
    mavenCentral() // <3>
}

dependencies {
}

application {
    mainClass.set("demo.AppKt") // <9>
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