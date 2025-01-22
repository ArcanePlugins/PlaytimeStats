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

package io.github.arcaneplugins.playtimestats.plugin.bukkit.command.playtimestats

import dev.jorel.commandapi.CommandAPICommand
import io.github.arcaneplugins.playtimestats.plugin.bukkit.PlaytimeStats
import io.github.arcaneplugins.playtimestats.plugin.bukkit.command.Cmd
import io.github.arcaneplugins.playtimestats.plugin.bukkit.command.playtimestats.subcommand.ReloadSubcmd
import io.github.arcaneplugins.playtimestats.plugin.bukkit.command.playtimestats.subcommand.VersionSubcmd
import io.github.arcaneplugins.playtimestats.plugin.core.misc.Permission

object PlaytimeStatsCmd : Cmd {
    override fun build(plugin: PlaytimeStats): CommandAPICommand {
        return CommandAPICommand("playtimestats")
            .withAliases("ptstats")
            .withPermission(Permission.COMMAND_PLAYTIMESTATS.toString())
            .withSubcommands(
                ReloadSubcmd.build(plugin),
                VersionSubcmd.build(plugin),
            )
    }
}