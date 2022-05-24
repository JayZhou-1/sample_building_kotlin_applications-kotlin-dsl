plugins {
    kotlin("jvm") version "1.6.10"
    `java-library`
//    application // <2>
}

repositories {
    mavenCentral() // <3>
}

dependencies {
    implementation(projects.appCommon)
    implementation("com.google.inject:guice:5.1.0")
    implementation("com.squareup.okhttp3:okhttp:4.0.1")
    implementation("org.reflections:reflections:0.9.10")

}

//application {
//    mainClass.set("demo.AppKt") // <9>
//}

//gradlePlugin {
//    plugins {
//        create("simplePlugin") {
//            id = "org.example.greeting"
//            implementationClass = "org.example.GreetingPlugin"
//        }
//    }
//}


