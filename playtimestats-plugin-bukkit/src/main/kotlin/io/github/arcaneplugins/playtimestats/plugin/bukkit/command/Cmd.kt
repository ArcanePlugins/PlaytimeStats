package io.github.arcaneplugins.playtimestats.plugin.bukkit.command

import dev.jorel.commandapi.CommandAPICommand
import io.github.arcaneplugins.playtimestats.plugin.bukkit.PlaytimeStats

interface Cmd {

    fun build(
        plugin: PlaytimeStats,
    ): CommandAPICommand

}