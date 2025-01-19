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

package io.github.arcaneplugins.playtimestats.plugin.bukkit.data

import io.github.arcaneplugins.playtimestats.plugin.bukkit.PlaytimeStats
import io.github.arcaneplugins.playtimestats.plugin.core.data.DataManager
import io.github.arcaneplugins.playtimestats.plugin.core.data.PlaytimeData
import org.bukkit.OfflinePlayer
import org.bukkit.Statistic
import org.bukkit.entity.Player
import kotlin.io.path.Path
import kotlin.io.path.pathString

class BukkitDataManager(
    private val plugin: PlaytimeStats
) : DataManager(
    platform = plugin,
    absolutePath = Path(plugin.dataFolder.absolutePath, "data", "h2.db").pathString,
) {

    private lateinit var updateDataTask: UpdateDataRunnable

    internal fun updatePlayerData(player: Player) {
        setPlaytimeData(PlaytimeData(
            uuid = player.uniqueId,
            lastUsername = player.name,
            minutesPlayed = getMinutesPlayed(player),
            sessionsPlayed = getSessionsPlayed(player),
        ))
    }

    fun getMinutesPlayed(player: OfflinePlayer): Int {
        return player.getStatistic(Statistic.PLAY_ONE_MINUTE)
    }

    fun getSessionsPlayed(player: OfflinePlayer): Int {
        return player.getStatistic(Statistic.LEAVE_GAME) + (if (player.isOnline) 1 else 0)
    }

    override fun startTasks() {
        updateDataTask = UpdateDataRunnable(plugin)
        updateDataTask.runTaskTimerAsynchronously(plugin, 0L, 20L * 60 * 10)
    }

    override fun stopTasks() {
        if (updateDataTask.isCancelled) {
            return
        }

        updateDataTask.cancel()
    }

}