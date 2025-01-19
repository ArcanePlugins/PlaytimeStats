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