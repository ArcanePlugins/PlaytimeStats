/*
 * Copyright (c) 2025 lokka30 and contributors.
 *
 * GPLv3 LICENSE NOTICE:
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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
