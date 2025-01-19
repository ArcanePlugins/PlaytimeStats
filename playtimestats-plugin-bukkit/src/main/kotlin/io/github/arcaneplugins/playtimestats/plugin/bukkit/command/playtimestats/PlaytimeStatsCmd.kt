package io.github.arcaneplugins.playtimestats.plugin.bukkit.command.playtimestats

import dev.jorel.commandapi.CommandAPICommand
import io.github.arcaneplugins.playtimestats.plugin.bukkit.PlaytimeStats
import io.github.arcaneplugins.playtimestats.plugin.bukkit.command.Cmd
import io.github.arcaneplugins.playtimestats.plugin.bukkit.command.playtimestats.subcommand.ReloadSubcmd
import io.github.arcaneplugins.playtimestats.plugin.bukkit.command.playtimestats.subcommand.VersionSubcmd
import io.github.arcaneplugins.playtimestats.plugin.bukkit.misc.Permission

object PlaytimeStatsCmd : Cmd {
    override fun build(plugin: PlaytimeStats): CommandAPICommand {
        return CommandAPICommand("playtimestats")
            .withPermission(Permission.COMMAND_PLAYTIMESTATS.toString())
            .withSubcommands(
                ReloadSubcmd.build(plugin),
                VersionSubcmd.build(plugin),
            )
    }
}