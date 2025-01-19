package io.github.arcaneplugins.playtimestats.plugin.bukkit.command

import dev.jorel.commandapi.CommandAPI
import dev.jorel.commandapi.CommandAPIBukkitConfig
import io.github.arcaneplugins.playtimestats.plugin.bukkit.PlaytimeStats
import io.github.arcaneplugins.playtimestats.plugin.bukkit.command.playtime.PlaytimeCmd
import io.github.arcaneplugins.playtimestats.plugin.bukkit.command.playtimestats.PlaytimeStatsCmd

class CmdManager(
    private val plugin: PlaytimeStats,
) {

    private val cmds = setOf(
        PlaytimeCmd,
        PlaytimeStatsCmd,
    )

    fun initialize() {
        CommandAPI.onLoad(
            CommandAPIBukkitConfig(plugin)
                .silentLogs(true)
                .verboseOutput(false)
                .usePluginNamespace()
        )

        registerCommands()
    }

    fun startup() {
        CommandAPI.onEnable()
    }

    fun shutdown() {
        CommandAPI.onDisable()
    }

    private fun registerCommands() {
        cmds.forEach { it.build(plugin).register() }
    }

}