package me.zodd.zoddJoin.actions.broadcast

import com.google.auto.service.AutoService
import me.zodd.zoddJoin.actions.AbstractAction
import me.zodd.zoddJoin.actions.Action
import me.zodd.zoddJoin.not
import org.spongepowered.api.Sponge
import org.spongepowered.api.entity.living.player.server.ServerPlayer

@AutoService(Action::class)
class  BroadcastAction : AbstractAction() {
    override val id = "BROADCAST"

    override fun run(player: ServerPlayer, data: String, isRunnable: Boolean) {

        if (isRunnable)
        Sponge.server().broadcastAudience().sendMessage(deserialize(data,player.name()))
    }
}