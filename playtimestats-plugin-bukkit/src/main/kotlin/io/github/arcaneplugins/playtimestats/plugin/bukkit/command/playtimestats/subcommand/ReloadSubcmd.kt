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
import io.github.arcaneplugins.playtimestats.plugin.core.misc.Permission

object ReloadSubcmd : Cmd {
    override fun build(plugin: PlaytimeStats): CommandAPICommand {
        return CommandAPICommand("reload")
            .withPermission(Permission.COMMAND_PLAYTIMESTATS_RELOAD.toString())
            .executes(CommandExecutor { sender, _ ->
                sender.sendMessage("Reloading...")
                try {
                    plugin.reload()
                } catch (ex: Exception) {
                    sender.sendMessage("Reload failed! See further details in console.")
                    sender.sendMessage("Exception message: ${ex.message}")
                    plugin.logger.severe("Encountered error whilst attempting to reload plugin:\n${ex.stackTraceToString()}")
                    return@CommandExecutor
                }
                sender.sendMessage("Reloaded successfully!")
            })
    }
}