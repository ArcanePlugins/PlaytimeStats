package io.github.arcaneplugins.playtimestats.plugin.core

interface Platform {

    companion object {
        const val SUPPORT_URL = "https://github.com/ArcanePlugins/.github/wiki/ArcanePlugins-Support"
    }

    fun initialize()

    fun startup()

    fun shutdown()

    fun reload()

}