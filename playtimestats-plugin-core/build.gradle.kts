plugins {
    kotlin("jvm")
}

group = "io.github.arcaneplugins.polyconomy.playtimestats"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}