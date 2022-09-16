package me.zodd.zoddJoin.actions.broadcast

import com.google.auto.service.AutoService
import me.zodd.zoddJoin.actions.AbstractAction
import me.zodd.zoddJoin.actions.Action
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.title.Title
import org.spongepowered.api.Sponge
import org.spongepowered.api.entity.living.player.server.ServerPlayer
import java.time.Duration

@AutoService(Action::class)
class TitleBroadcastAction : AbstractAction() {
    override val id = "TITLEBROADCAST"

    override fun run(player: ServerPlayer, data: String, isRunnable: Boolean) {
        val args = data.split(";")
        val name = player.name()

        val (title, subtitle) = args
        val fadeIn = args.getOrNull(3)?.toIntOrNull() ?: DEFAULT_FADE_IN
        val stay = args.getOrNull(4)?.toIntOrNull() ?: DEFAULT_STAY
        val fadeOut = args.getOrNull(5)?.toIntOrNull() ?: DEFAULT_FADE_OUT

        val mainTitle = deserialize(title,name)
        val subTitle = deserialize(subtitle,name)

        val times = Title.Times.times(
            Duration.ofSeconds(fadeIn.toLong()),
            Duration.ofSeconds(stay.toLong()),
            Duration.ofSeconds(fadeOut.toLong())
        )

        val timesTitle = Title.title(mainTitle, subTitle, times)

        if (isRunnable)
        Sponge.server().onlinePlayers().forEach { it.showTitle(timesTitle) }

    }

    private val DEFAULT_FADE_IN = 5
    private val DEFAULT_STAY = 10
    private val DEFAULT_FADE_OUT = 5
}