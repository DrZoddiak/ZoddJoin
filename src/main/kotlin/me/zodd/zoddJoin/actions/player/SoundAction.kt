package me.zodd.zoddJoin.actions.player

import com.google.auto.service.AutoService
import me.zodd.zoddJoin.actions.AbstractAction
import me.zodd.zoddJoin.actions.Action
import net.kyori.adventure.sound.Sound
import org.spongepowered.api.ResourceKey
import org.spongepowered.api.entity.living.player.server.ServerPlayer

@AutoService(Action::class)
class SoundAction : AbstractAction() {
    override val id = "SOUND"

    override fun run(player: ServerPlayer, data: String, isRunnable: Boolean) {
        if (!isRunnable) return

        val args = data.split(";")
        val sound = Sound.sound(
            ResourceKey.resolve(args[0]),
            Sound.Source.MASTER,
            args.getOrNull(1)?.toFloat() ?: 100f,
            args.getOrNull(1)?.toFloat() ?: 1f
        )
        player.playSound(sound)
    }
}