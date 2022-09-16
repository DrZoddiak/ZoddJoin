package me.zodd.zoddJoin.listeners

import me.zodd.zoddJoin.cache.ConfigCache.quitFormatCache
import me.zodd.zoddJoin.cache.ConfigCache.values
import org.spongepowered.api.event.Listener
import org.spongepowered.api.event.network.ServerSideConnectionEvent
import org.spongepowered.plugin.PluginContainer

class QuitListener(plugin: PluginContainer) : AbstractListener(plugin) {

    @Listener
    fun playerQuit(event: ServerSideConnectionEvent.Disconnect) {
        val player = event.player()

        processFormatSettings(quitFormatCache.values(),player)
    }
}