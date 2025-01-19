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

package io.github.arcaneplugins.playtimestats.plugin.bukkit.command.playtimestats.subcommand

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.executors.CommandExecutor
import io.github.arcaneplugins.playtimestats.plugin.bukkit.PlaytimeStats
import io.github.arcaneplugins.playtimestats.plugin.bukkit.command.Cmd
import io.github.arcaneplugins.playtimestats.plugin.bukkit.misc.Permission
import io.github.arcaneplugins.playtimestats.plugin.core.Platform
import org.bukkit.ChatColor
import org.bukkit.ChatColor.AQUA
import org.bukkit.ChatColor.DARK_GRAY
import org.bukkit.ChatColor.GRAY
import org.bukkit.ChatColor.WHITE

object VersionSubcmd : Cmd {
    override fun build(plugin: PlaytimeStats): CommandAPICommand {
        return CommandAPICommand("version")
            .withAliases("ver", "about", "info")
            .withPermission(Permission.COMMAND_PLAYTIMESTATS_VERSION.toString())
            .executes(CommandExecutor { sender, _ ->
                val authors = plugin.description.authors.joinToString(
                    separator = ", ",
                    prefix = GRAY.toString(),
                    postfix = DARK_GRAY.toString(),
                )

                sender.sendMessage(
                    """
                    
                    ${WHITE}${ChatColor.BOLD}PlaytimeStats v${plugin.description.version}${GRAY} from ArcanePlugins
                      ${DARK_GRAY}•${AQUA} Authors: ${GRAY}${authors}
                      ${DARK_GRAY}•${AQUA} Description: ${GRAY}${plugin.description.description}
                      ${DARK_GRAY}•${AQUA} Website: ${GRAY}${plugin.description.website}
                      ${DARK_GRAY}•${AQUA} Support Link: ${GRAY}${Platform.SUPPORT_URL}
                    
                """.trimIndent()
                )
            })
    }
}