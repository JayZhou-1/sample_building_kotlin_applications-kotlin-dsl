plugins {
    kotlin("jvm") version "1.6.10"
    `java-gradle-plugin`

    application // <2>
}

repositories {
    mavenCentral() // <3>
}

dependencies {
    implementation(projects.appCommon)
    implementation(platform("org.jetbrains.kotlin:kotlin-bom")) // <4>

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8") // <5>

    implementation("com.google.guava:guava:30.1.1-jre") // <6>

    implementation("com.google.inject:guice:5.1.0")
    implementation("com.squareup.okhttp3:okhttp:4.0.1")



    testImplementation("org.jetbrains.kotlin:kotlin-test") // <7>

    testImplementation("org.jetbrains.kotlin:kotlin-test-junit") // <8>
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