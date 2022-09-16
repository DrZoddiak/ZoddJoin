package me.zodd.zoddJoin.actions.player

import com.google.auto.service.AutoService
import me.zodd.zoddJoin.actions.AbstractAction
import me.zodd.zoddJoin.actions.Action
import me.zodd.zoddJoin.not
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.title.Title
import net.kyori.adventure.title.TitlePart
import org.spongepowered.api.entity.living.player.server.ServerPlayer
import java.time.Duration

@AutoService(Action::class)
class TitleMessageAction : AbstractAction() {
    override val id = "TITLEMESSAGE"

    private val DEFAULT_FADE_IN = 5
    private val DEFAULT_STAY = 10
    private val DEFAULT_FADE_OUT = 5

    override fun run(player: ServerPlayer, data: String, isRunnable: Boolean) {
        if (!isRunnable) return
        val name = player.name()

        val args = data.split(";")

        val (title, subtitle) = args
        val fadeIn = args.getOrNull(3)?.toIntOrNull() ?: DEFAULT_FADE_IN
        val stay = args.getOrNull(4)?.toIntOrNull() ?: DEFAULT_STAY
        val fadeOut = args.getOrNull(5)?.toIntOrNull() ?: DEFAULT_FADE_OUT

        val mainTitle = deserialize(title, name)
        val subTitle = deserialize(subtitle, name)

        val times = Title.Times.times(
            Duration.ofSeconds(fadeIn.toLong()),
            Duration.ofSeconds(stay.toLong()),
            Duration.ofSeconds(fadeOut.toLong())
        )

        val timesTitle = Title.title(mainTitle, subTitle, times)

        player.showTitle(timesTitle)
    }
}