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

package io.github.arcaneplugins.playtimestats.plugin.bukkit.command.playtimetop

import dev.jorel.commandapi.CommandAPI
import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.arguments.IntegerArgument
import dev.jorel.commandapi.executors.CommandExecutor
import io.github.arcaneplugins.playtimestats.plugin.bukkit.PlaytimeStats
import io.github.arcaneplugins.playtimestats.plugin.bukkit.PlaytimeStats.Companion.MINUTES_DECIMAL_FORMAT
import io.github.arcaneplugins.playtimestats.plugin.bukkit.command.Cmd
import io.github.arcaneplugins.playtimestats.plugin.core.misc.Permission
import org.bukkit.Bukkit
import org.bukkit.ChatColor.AQUA
import org.bukkit.ChatColor.BOLD
import org.bukkit.ChatColor.DARK_GRAY
import org.bukkit.ChatColor.GRAY
import org.bukkit.ChatColor.RESET
import org.bukkit.ChatColor.STRIKETHROUGH
import org.bukkit.ChatColor.WHITE
import kotlin.jvm.optionals.getOrNull

object PlaytimeTopCmd: Cmd {

    override fun build(plugin: PlaytimeStats): CommandAPICommand {
        return CommandAPICommand("playtimetop")
            .withAliases("pttop")
            .withPermission(Permission.COMMAND_PLAYTIMETOP.toString())
            .withOptionalArguments(
                IntegerArgument("page")
            )
            .executes(CommandExecutor { sender, args ->
                val page = (args.getOptional("page").getOrNull() as Int?) ?: 1

                if (page <= 0) {
                    throw CommandAPI.failWithString("Page number must be at least 1")
                }

                // update playtimes of all online players so it displays the latest information
                Bukkit.getOnlinePlayers().forEach(plugin.dataMgr::updatePlayerData)

                val dataList = plugin.dataMgr.getTopPlaytimesData(page)

                sender.sendMessage("${DARK_GRAY}${STRIKETHROUGH}+---------+${WHITE}${BOLD} Top Playtimes ${DARK_GRAY}•${GRAY} pg${page} ${DARK_GRAY}${STRIKETHROUGH}+---------+${RESET}")
                dataList.forEachIndexed { index, data ->
                    sender.sendMessage("${DARK_GRAY}  ${index + 1}.  ${AQUA}${data.lastUsername}${DARK_GRAY} • ${GRAY}${MINUTES_DECIMAL_FORMAT.format(data.minutesPlayed)} minutes${DARK_GRAY}, ${GRAY}${data.sessionsPlayed} sessions")
                }
                sender.sendMessage("")
            })
    }


}