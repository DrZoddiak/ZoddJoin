package me.zodd.zoddJoin.actions.player

import com.google.auto.service.AutoService
import me.zodd.zoddJoin.actions.AbstractAction
import me.zodd.zoddJoin.actions.Action
import org.spongepowered.api.Sponge
import org.spongepowered.api.entity.living.player.server.ServerPlayer

@AutoService(Action::class)
class ConsoleCommandAction : AbstractAction() {
    override val id = "CONSOLECOMMAND"

    override fun run(player: ServerPlayer, data: String, isRunnable: Boolean) {
        if (isRunnable)
        Sponge.server().commandManager().process(data)
    }
}