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

package io.github.arcaneplugins.playtimestats.plugin.bukkit.command.playtime

import dev.jorel.commandapi.CommandAPI
import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.arguments.OfflinePlayerArgument
import dev.jorel.commandapi.executors.CommandExecutor
import io.github.arcaneplugins.playtimestats.plugin.bukkit.PlaytimeStats
import io.github.arcaneplugins.playtimestats.plugin.bukkit.command.Cmd
import io.github.arcaneplugins.playtimestats.plugin.core.data.PlaytimeData
import io.github.arcaneplugins.playtimestats.plugin.core.misc.Permission
import org.bukkit.ChatColor.AQUA
import org.bukkit.ChatColor.DARK_GRAY
import org.bukkit.ChatColor.GRAY
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import kotlin.jvm.optionals.getOrNull

object PlaytimeCmd : Cmd {
    override fun build(plugin: PlaytimeStats): CommandAPICommand {
        return CommandAPICommand("playtime")
            .withPermission(Permission.COMMAND_PLAYTIME.toString())
            .withAliases("pt")
            .withOptionalArguments(
                OfflinePlayerArgument("player")
            )
            .executes(CommandExecutor { sender, args ->
                val player = (args.getOptional("player").getOrNull() as OfflinePlayer?)
                    ?: if (sender is Player) {
                        sender
                    } else {
                        null
                    }
                    ?: throw CommandAPI.failWithString("Specify a player to check the playtime of.")

                if (!player.hasPlayedBefore()) {
                    throw CommandAPI.failWithString("That player hasn't played before.")
                }

                val data = plugin.dataMgr.getPlaytimeData(player.uniqueId) ?: PlaytimeData(
                    uuid = player.uniqueId,
                    lastUsername = player.name ?: "?",
                    minutesPlayed = plugin.dataMgr.getMinutesPlayed(player),
                    sessionsPlayed = plugin.dataMgr.getSessionsPlayed(player),
                )

                sender.sendMessage("${GRAY}Playtime statistics for ${AQUA}${data.lastUsername}$GRAY:")
                sender.sendMessage("${DARK_GRAY} • ${GRAY}Time played: ${AQUA}${data.minutesPlayed}} minutes")
                sender.sendMessage("${DARK_GRAY} • ${GRAY}Sessions played: ${AQUA}${data.sessionsPlayed}}")
            })
    }
}