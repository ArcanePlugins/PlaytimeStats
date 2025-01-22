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

package demo

import io.github.arcaneplugins.playtimestats.plugin.core.Platform
import io.github.arcaneplugins.playtimestats.plugin.core.data.DataManager
import kotlin.io.path.Path
import kotlin.io.path.absolute
import kotlin.io.path.pathString

class TestDataManager(platform: Platform) : DataManager(platform, absolutePath = ABSOLUTE_PATH.pathString) {

    companion object {
        val ABSOLUTE_PATH = Path("TEST_DATA/h2.db").absolute()
    }

    override fun startTasks() {
        // not needed
    }

    override fun stopTasks() {
        // not needed
    }
}