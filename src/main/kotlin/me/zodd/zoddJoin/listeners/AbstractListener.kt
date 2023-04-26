package me.zodd.zoddJoin.listeners

import me.zodd.zoddJoin.cache.ConfigCache
import me.zodd.zoddJoin.cache.FormatHolder
import me.zodd.zoddJoin.cache.Holder
import me.zodd.zoddJoin.cache.MotdHolder
import me.zodd.zoddJoin.actions.ActionHolder
import org.spongepowered.api.Sponge
import org.spongepowered.api.effect.VanishState
import org.spongepowered.api.entity.living.player.server.ServerPlayer
import org.spongepowered.api.scheduler.Task
import org.spongepowered.plugin.PluginContainer
import java.time.Duration

abstract class AbstractListener(private val plugin: PluginContainer) {
    private fun scheduleTask(delay: Long, e: () -> Unit) {
        val task = Task.builder()
            .plugin(plugin)
            .delay(Duration.ofSeconds(delay))
            .execute(e)
            .build()
        Sponge.server().scheduler().submit(task)
    }

    private fun <T : Holder> filter(holders: MutableCollection<out T>, player: ServerPlayer): T {
        return holders
            .filter { player.hasPermission(it.settings.permission) || it.settings.permission.isBlank() }
            .maxBy { it.settings.priority }
    }

    private fun handleActionHolder(holder: ActionHolder, delay: Long = 0L, run: () -> Unit) {
        val combineDelay = holder.delay + delay
        if (combineDelay > 0) {
            scheduleTask(combineDelay, run)
        } else {
            run.invoke()
        }
    }

    fun processFormatSettings(holders: MutableCollection<FormatHolder>, player: ServerPlayer) {
        val formatHolder = filter(holders, player)

        when (formatHolder.settings.type) {
            "NORMAL" -> {
                formatHolder.actions.forEach { holder ->
                    handleActionHolder(holder) {
                        holder.pair.action.forEach {
                            it.run(player, holder.content, holder.isRunnable)
                        }
                    }
                }
            }

            "RANDOM" -> {
                val act = formatHolder.actions.random()
                handleActionHolder(act) {
                    act.pair.action.random().run(player, act.content, act.isRunnable)
                }
            }

            "VANISH" -> {
                if (player.vanishState().get() == VanishState.vanished()) {

                }
            }
        }
    }

    fun processMotdSettings(motdHolders: MutableCollection<MotdHolder>, player: ServerPlayer) {

        val motdHolder = filter(motdHolders, player)

        val delay = motdHolder.settings.delay.toLong()
        motdHolder.action.forEach { holder ->
            handleActionHolder(holder, delay) {
                holder.pair.action.forEach {
                    it.run(player, holder.content, holder.isRunnable)
                }
            }
        }
    }

    fun processFirstJoin(player: ServerPlayer) {
        ConfigCache.firstJoinMessageCache.forEach { holder ->
            handleActionHolder(holder) {
                holder.pair.action.forEach {
                    it.run(player, holder.content, holder.isRunnable)
                }
            }
        }
    }
}