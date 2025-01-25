import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

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
}

apply(plugin = "java")
apply(plugin = "kotlin")

repositories {
    mavenCentral()
}

dependencies {
    api(libs.h2)
    testImplementation(kotlin("test"))
}

publishing {
    publications {
        withType<MavenPublication>().configureEach {
            if (name == "gpr") {
                artifacts.clear()
                artifact(tasks.named("shadowJar").get()) {
                    classifier = "all"
                }
            }
        }
    }
}

tasks {
    jar {
        enabled = true
    }

    compileKotlin {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_21
            apiVersion = KotlinVersion.KOTLIN_2_1
        }
    }

    compileTestKotlin {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_21
            apiVersion = KotlinVersion.KOTLIN_2_1
        }
    }

    compileJava {
        options.isDeprecation = true
        options.encoding = "UTF-8"
    }

    test {
        useJUnitPlatform()
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}
