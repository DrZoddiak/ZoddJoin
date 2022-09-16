package me.zodd.zoddJoin.actions.player

import com.google.auto.service.AutoService
import me.zodd.zoddJoin.actions.AbstractAction
import me.zodd.zoddJoin.actions.Action
import me.zodd.zoddJoin.not
import org.spongepowered.api.entity.living.player.server.ServerPlayer

@AutoService(Action::class)
class ActionbarMessageAction : AbstractAction() {
    override val id = "ACTIONBARMESSAGE"

    override fun run(player: ServerPlayer, data: String, isRunnable: Boolean) {
        if (isRunnable)
        player.sendActionBar(deserialize(data,player.name()))
    }
}