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

package io.github.arcaneplugins.playtimestats.plugin.bukkit.integration.std

import com.earth2me.essentials.Essentials
import io.github.arcaneplugins.playtimestats.plugin.bukkit.PlaytimeStats
import io.github.arcaneplugins.playtimestats.plugin.bukkit.integration.Integration
import net.ess3.api.IUser
import net.ess3.api.events.AfkStatusChangeEvent
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener

class EssentialsXIntegration(
    plugin: PlaytimeStats
) : Integration(plugin, "EssentialsX") {

    private lateinit var ess: Essentials
    private val listener = AfkStatusChangeListener(plugin)

    override fun canConnect(): Boolean {
        return enabled && Bukkit.getPluginManager().isPluginEnabled("Essentials")
    }

    override fun startup() {
        if (connected || !canConnect()) {
            return
        }

        plugin.logger.info("Hooking into ${name}.")

        ess = Bukkit.getPluginManager().getPlugin("Essentials") as Essentials

        plugin.server.pluginManager.registerEvents(listener, plugin)

        connected = true
    }

    override fun shutdown() {
        if (!connected) {
            return
        }

        plugin.logger.info("Unhooking from ${name}.")

        AfkStatusChangeEvent.getHandlerList().unregister(listener)

        connected = false
    }

    // This event already fires when players leave, so only need to listen to the one event.
    // We could add a player quit listener just in case, if you want...
    class AfkStatusChangeListener(
        plugin: PlaytimeStats,
    ) : Listener {

        private val dataMgr = plugin.dataMgr

        @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
        fun onAfkStatusChange(event: AfkStatusChangeEvent) {
            val isNowAfk: Boolean = event.value
            val user: IUser = event.affected
            val player: Player = user.base

            // update gross playtime etc so new net time is accurate
            dataMgr.updatePlayerData(player)

            val ptData = dataMgr.getOrCreatePlaytimeData(user.uuid, user.name)
            if (isNowAfk) {
                dataMgr.playerBeginAfkSession(ptData)
            } else {
                dataMgr.playerEndAfkSession(ptData)
            }
        }
    }

}