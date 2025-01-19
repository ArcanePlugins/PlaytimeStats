package io.github.arcaneplugins.playtimestats.plugin.bukkit.misc

import java.util.*

enum class Permission(
    val default: Boolean = false,
) {

    COMMAND_PLAYTIME(default = true),
    COMMAND_PLAYTIMESTATS(default = true),
    COMMAND_PLAYTIMESTATS_RELOAD,
    COMMAND_PLAYTIMESTATS_VERSION(default = true);

    override fun toString(): String {
        return "playtimestats." + name
            .lowercase(Locale.ROOT)
            .replace('_', '.')
    }

}