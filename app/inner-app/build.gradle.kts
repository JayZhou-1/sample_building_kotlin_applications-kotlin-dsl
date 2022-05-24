plugins {
    kotlin("jvm") version "1.6.10"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":app-common"))
}

application {
    mainClass.set("com.faire.InnerAppKt")
}


