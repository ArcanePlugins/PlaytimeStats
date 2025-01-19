package io.github.arcaneplugins.playtimestats.plugin.bukkit.command.playtime

import dev.jorel.commandapi.CommandAPI
import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.arguments.OfflinePlayerArgument
import dev.jorel.commandapi.executors.CommandExecutor
import io.github.arcaneplugins.playtimestats.plugin.bukkit.PlaytimeStats
import io.github.arcaneplugins.playtimestats.plugin.bukkit.command.Cmd
import io.github.arcaneplugins.playtimestats.plugin.bukkit.misc.Permission
import org.bukkit.ChatColor.AQUA
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

                sender.sendMessage("${GRAY}Retrieving playtime for ${AQUA}${player.name}${GRAY}...")
                throw CommandAPI.failWithString("This command is not yet implemented.") // TODO Implement CMD
            })
    }
}