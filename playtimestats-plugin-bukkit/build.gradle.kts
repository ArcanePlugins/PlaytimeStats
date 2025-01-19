import org.jetbrains.kotlin.gradle.dsl.JvmTarget

group = "io.github.arcaneplugins.polyconomy.playtimestats"
description = description
version = version

plugins {
    id("java")
    kotlin("jvm") version "2.1.0"
    id("com.gradleup.shadow") version "8.3.5" // the IDE bugs out with 9.0.0-beta4 - maybe wait for the 9.x release?
}

apply(plugin = "java")
apply(plugin = "kotlin")
apply(plugin = "com.gradleup.shadow")

repositories {
    mavenCentral()
    gradlePluginPortal()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
}

dependencies {
    implementation(project.parent!!.project("playtimestats-plugin-core"))
    implementation(libs.bStats)
    implementation(libs.commandApi)
    compileOnly(libs.spigotApi)
    compileOnly(libs.placeholderApi)
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

tasks {
    jar {
        enabled = false
    }

    shadowJar {
        archiveClassifier = ""
        dependencies {
            relocate("dev.jorel.commandapi", "${project.group}.plugin.bukkit.lib.commandapi")
            relocate("org.bstats", "${project.group}.plugin.bukkit.lib.bstats")
            relocate("kotlin", "${project.group}.plugin.bukkit.lib.kotlin")
        }
        minimize {}
    }

    compileKotlin {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_21
            apiVersion = org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_1
        }
    }

    compileJava {
        options.isDeprecation = true
        options.encoding = "UTF-8"
    }

    processResources {
        val properties = mapOf(
            "version" to project.version,
            "description" to project.description,
        )

        inputs.properties(properties)
        filteringCharset = "UTF-8"
        outputs.upToDateWhen { false }
        filesMatching("plugin.yml") {
            expand(properties)
        }
    }
}
