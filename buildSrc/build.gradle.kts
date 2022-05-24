plugins {
    `kotlin-dsl`
    `java-gradle-plugin`

    kotlin("jvm") version "1.6.10"

}

repositories {
    mavenCentral()
}

dependencies{
    implementation("org.reflections:reflections:0.9.10")
}