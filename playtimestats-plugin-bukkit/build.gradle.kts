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
    kotlin("jvm") version "2.1.0"
    alias(libs.plugins.shadow)
}

apply(plugin = "java")
apply(plugin = "kotlin")

repositories {
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://repo.essentialsx.net/releases/")
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    implementation(project(":playtimestats-plugin-core"))
    implementation(libs.bStats)
    implementation(libs.commandApi)
    compileOnly(libs.h2)
    compileOnly(libs.paperApi)
    compileOnly(libs.placeholderApi)
    compileOnly(libs.essentialsX)
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

tasks {
    shadowJar {
        dependsOn(":playtimestats-plugin-core:shadowJar")
        dependencies {
            relocate("dev.jorel.commandapi", "${project.group}.plugin.bukkit.lib.commandapi")
            relocate("org.bstats", "${project.group}.plugin.bukkit.lib.bstats")
            relocate("kotlin", "${project.group}.plugin.bukkit.lib.kotlin")
        }
    }

    processResources {
        val properties = mapOf(
            "version" to project.version,
            "description" to project.description,
            "h2Version" to libs.versions.h2Version.get()
        )

        inputs.properties(properties)
        filteringCharset = "UTF-8"
        outputs.upToDateWhen { false }
        filesMatching("plugin.yml") {
            expand(properties)
        }
    }
}
