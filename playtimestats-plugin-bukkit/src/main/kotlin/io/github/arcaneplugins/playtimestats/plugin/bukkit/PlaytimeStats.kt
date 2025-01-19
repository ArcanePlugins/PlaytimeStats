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

package io.github.arcaneplugins.playtimestats.plugin.bukkit

import io.github.arcaneplugins.playtimestats.plugin.bukkit.command.CmdManager
import io.github.arcaneplugins.playtimestats.plugin.bukkit.data.BukkitDataManager
import io.github.arcaneplugins.playtimestats.plugin.bukkit.listener.ListenerManager
import io.github.arcaneplugins.playtimestats.plugin.core.Platform
import org.bukkit.plugin.java.JavaPlugin

class PlaytimeStats : JavaPlugin(), Platform {

    private val cmdMgr = CmdManager(this)
    internal val dataMgr = BukkitDataManager(this)
    private val listenerMgr = ListenerManager(this)

    override fun onLoad() {
        initialize()
    }

    override fun onEnable() {
        startup()
    }

    override fun onDisable() {
        shutdown()
    }

    override fun initialize() {
        cmdMgr.initialize()
    }

    override fun startup() {
        dataMgr.startup()
        listenerMgr.startup()
        cmdMgr.startup()
    }

    override fun shutdown() {
        cmdMgr.shutdown()
        dataMgr.shutdown()
    }

    override fun reload() {
        dataMgr.shutdown()
        dataMgr.startup()
    }

}