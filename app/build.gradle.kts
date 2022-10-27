plugins {
    kotlin("jvm") version "1.6.10"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(projects.appCommon)
    implementation("com.google.inject:guice:5.1.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    implementation("com.squareup.retrofit2:retrofit:2.6.2")
    implementation("com.squareup.retrofit2:converter-gson:2.6.2")
    implementation("org.reflections:reflections:0.9.10")
    implementation("io.github.cdimascio:dotenv-kotlin:6.2.2")
    implementation("com.amazonaws:aws-java-sdk-s3:1.12.112")

    testImplementation("junit:junit:4.12")
    testImplementation("org.assertj:assertj-core:3.20.2")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.9.3")
}

application {
    mainClass.set("com.faire.AppKt")
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