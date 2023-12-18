plugins {
    kotlin("jvm") version "1.9.21"
}

group = "com.spkd.cricket"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}


dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    implementation("no.tornado:tornadofx:1.7.20")
    implementation("org.openjfx:javafx-controls:16")
    implementation("org.openjfx:javafx-fxml:16")
    implementation("org.openjfx:javafx-graphics:16")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(20)
}