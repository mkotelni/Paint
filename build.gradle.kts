plugins {
    id("java")
    id("application")
    id("org.openjfx.javafxplugin") version "0.1.0"
    kotlin("jvm")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation(kotlin("stdlib-jdk8"))
}

javafx{
    version = "22"
    modules = listOf("javafx.controls", "javafx.fxml", "javafx.swing")
}

application {
    mainClass.set("org.example.Main")
}

// --- Include dependencies in the JAR (fat JAR) --- CHATGPT CODE
tasks.register<Jar>("fatJar") {
    group = "build"
    description = "Builds a runnable fat JAR that includes all dependencies"

    archiveClassifier.set("all")
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    manifest {
        attributes["Main-Class"] = application.mainClass.get()
    }

    // Include compiled classes
    from(sourceSets.main.get().output)

    // Include dependency JARs
    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get()
            .filter { it.name.endsWith("jar") }
            .map { zipTree(it) }
    })
}

// Ensure fatJar is built when running "build" CHATGPT CODE
tasks.build {
    dependsOn("fatJar")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}