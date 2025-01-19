import org.jetbrains.kotlin.gradle.dsl.JvmTarget

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

plugins {
    kotlin("jvm")
    id("com.gradleup.shadow") version "8.3.5"
}

apply(plugin = "java")
apply(plugin = "kotlin")
apply(plugin = "com.gradleup.shadow")

group = "io.github.arcaneplugins.polyconomy.playtimestats"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.h2)
    testImplementation(kotlin("test"))
}

tasks {
    jar {
        enabled = false
    }

    shadowJar {
        archiveClassifier = ""
        dependencies {
            relocate("org.h2", "${project.group}.plugin.core.lib.h2")
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
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

kotlin {
    jvmToolchain(21)
}