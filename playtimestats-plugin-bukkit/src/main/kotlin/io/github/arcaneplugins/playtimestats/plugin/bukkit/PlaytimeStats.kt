package io.github.arcaneplugins.playtimestats.plugin.bukkit

import io.github.arcaneplugins.playtimestats.plugin.bukkit.command.CmdManager
import io.github.arcaneplugins.playtimestats.plugin.core.Platform
import org.bukkit.plugin.java.JavaPlugin

class PlaytimeStats : JavaPlugin(), Platform {

    private val cmdMgr = CmdManager(this)

    override fun onLoad() {
        initialize()
    }

    override fun onEnable() {
        startup()
    }

    override fun onDisable() {
        shutdown()
    }

    override fun initialize() {
        cmdMgr.initialize()
    }

    override fun startup() {
        cmdMgr.startup()
    }

    override fun shutdown() {
        cmdMgr.shutdown()
    }

    override fun reload() {
        TODO("Not yet implemented")
    }

}