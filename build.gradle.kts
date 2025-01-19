group = "io.github.arcaneplugins.polyconomy.playtimestats"

allprojects {
    repositories {
        mavenCentral()
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }
}

plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.shadow)
}

subprojects {
    apply {
        plugin("org.jetbrains.kotlin.jvm")
        plugin("com.gradleup.shadow")
    }

    tasks {
        shadowJar {
            from("$rootDir/LICENSE.md")
        }

        build {
            dependsOn(shadowJar)
        }
    }

    dependencies {
        implementation(kotlin("stdlib"))
    }
}
