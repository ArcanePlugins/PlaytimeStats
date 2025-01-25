
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

allprojects {
    repositories {
        mavenCentral()
    }

    group = "io.github.arcaneplugins.playtimestats"
}

plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.shadow)
    id("maven-publish")
}

subprojects {
    apply {
        plugin("org.jetbrains.kotlin.jvm")
        plugin("com.gradleup.shadow")
        plugin("maven-publish")
        plugin("com.gradleup.shadow")
    }

    tasks {
        jar {
            enabled = false
        }

        shadowJar {
            archiveClassifier = ""
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

        compileTestJava {
            sourceCompatibility = "21"
            targetCompatibility = "21"
        }

        compileJava {
            options.isDeprecation = true
            options.encoding = "UTF-8"
        }

        test {
            useJUnitPlatform()
        }
    }

    configure<PublishingExtension> {
        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/ArcanePlugins/PlaytimeStats")
                credentials {
                    username = project.findProperty("gpr.user") as String? ?: System.getenv("GITHUB_ACTOR")
                    password = project.findProperty("gpr.key") as String? ?: System.getenv("GITHUB_TOKEN")
                }
            }
            publications {
                register<MavenPublication>("gpr") {
                    artifact(tasks.named("shadowJar")) {
                        builtBy(tasks.named("shadowJar"))
                    }

                    from(components["java"])

                    pom {
                        name = project.name
                        description = project.description
                        url = "https://github.com/ArcanePlugins/PlaytimeStats"

                        licenses {
                            license {
                                name = "GNU GPL v3"
                                url = "https://gnu.org"
                            }
                        }

                        developers {
                            developer {
                                id = "lokka30"
                                name = "lokka30"
                                email = "lachy@lachy.space"
                            }
                        }

                        scm {
                            connection = "scm:git:git://github.com/ArcanePlugins/PlaytimeStats.git"
                            developerConnection = "scm:git:ssh://github.com:ArcanePlugins/PlaytimeStats.git"
                            url = "http://github.com/ArcanePlugins/PlaytimeStats"
                        }
                    }
                }
            }
        }
    }

    tasks {
        shadowJar {
            from("$rootDir/LICENSE.md")
        }

        build {
            dependsOn(shadowJar)
        }

        publish {
            dependsOn(build)
        }
    }

    dependencies {
        implementation(kotlin("stdlib"))
    }
}
