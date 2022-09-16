package me.zodd.zoddJoin.listeners

import me.zodd.zoddJoin.cache.ConfigCache.joinFormatCache
import me.zodd.zoddJoin.cache.ConfigCache.motdCache
import me.zodd.zoddJoin.cache.ConfigCache.values
import org.spongepowered.api.event.Listener
import org.spongepowered.api.event.network.ServerSideConnectionEvent
import org.spongepowered.plugin.PluginContainer

class JoinListener(plugin: PluginContainer) : AbstractListener(plugin) {

    @Listener
    fun playerJoin(event: ServerSideConnectionEvent.Join) {
        val player = event.player()
        val firstTime = !player.hasPlayedBefore()
        event.isMessageCancelled = true

        processMotdSettings(motdCache.values(),player)

        if (firstTime) {
            processFirstJoin(player)
            return
        }

        processFormatSettings(joinFormatCache.values(),player)
    }
}