package io.github.arcaneplugins.playtimestats.plugin.bukkit.command.playtimestats.subcommand

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.executors.CommandExecutor
import io.github.arcaneplugins.playtimestats.plugin.bukkit.PlaytimeStats
import io.github.arcaneplugins.playtimestats.plugin.bukkit.command.Cmd
import io.github.arcaneplugins.playtimestats.plugin.bukkit.misc.Permission

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