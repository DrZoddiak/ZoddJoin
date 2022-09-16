package me.zodd.zoddJoin.actions.broadcast

import com.google.auto.service.AutoService
import me.zodd.zoddJoin.actions.AbstractAction
import me.zodd.zoddJoin.actions.Action
import me.zodd.zoddJoin.not
import org.spongepowered.api.Sponge
import org.spongepowered.api.entity.living.player.server.ServerPlayer

@AutoService(Action::class)
class AudienceBroadcastAction : AbstractAction() {
    override val id = "AUDIENCEBROADCAST"

    override fun run(player: ServerPlayer, data: String, isRunnable: Boolean) {
        if (isRunnable)
        Sponge.server().onlinePlayers().forEach {
            if (player == it) return@forEach
            it.sendMessage(deserialize(data,player.name()))
        }
    }
}