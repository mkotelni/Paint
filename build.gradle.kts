plugins {
    id("java")
    id("application")
    id("org.openjfx.javafxplugin") version "0.1.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

javafx{
    version = "22"
    modules = listOf("javafx.controls", "javafx.fxml", "javafx.swing")
}

application {
    mainClass.set("org.example.Main")
}

tasks.test {
    useJUnitPlatform()
}