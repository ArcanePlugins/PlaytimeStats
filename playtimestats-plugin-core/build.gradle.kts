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

dependencies {
    api(libs.h2)
    testImplementation(kotlin("test"))
}

/*
WARNING FOR THIS MODULE:
DO NOT MINIMIZE shadowJar's JAR - CAUSES LIBRARY MODULES TO NOT INCLUDE NECESSARY CLASSES
 */

publishing {
    publications {
        withType<MavenPublication>().configureEach {
            if (name == "gpr") {
                artifacts.clear()
                artifact(tasks.named("shadowJar").get()) {
                    classifier = ""
                }
            }
        }
    }
}
