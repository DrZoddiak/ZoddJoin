package me.zodd.zoddJoin.actions.player

import com.google.auto.service.AutoService
import me.zodd.core.logger
import me.zodd.zoddJoin.actions.AbstractAction
import me.zodd.zoddJoin.actions.Action
import org.spongepowered.api.ResourceKey
import org.spongepowered.api.Sponge
import org.spongepowered.api.entity.living.player.server.ServerPlayer
import org.spongepowered.api.world.Location
import org.spongepowered.api.world.LocationCreator
import org.spongepowered.api.world.World
import org.spongepowered.api.world.WorldType
import org.spongepowered.api.world.WorldTypes
import org.spongepowered.math.vector.Vector3d
import java.util.stream.Collectors

@AutoService(Action::class)
class TeleportAction : AbstractAction() {
    override val id = "TELEPORT"

    override fun run(player: ServerPlayer, data: String, isRunnable: Boolean) {
        if (!isRunnable) return

        val args = data.split(";")

        logger<TeleportAction>().info(WorldTypes.registry().streamEntries().map { it.key() }.collect(Collectors.toSet()).joinToString())

        val worldKey = ResourceKey.resolve(args[0])
        val locX = args[1].toDouble()
        val locY = args[2].toDouble()
        val locZ = args[3].toDouble()

        val worldType = WorldTypes.registry().findEntry<WorldType>(worldKey).get().value()
        val location = Vector3d.from(locX, locY, locZ)

        player.sendWorldType(worldType)
        player.setPosition(location)
    }
}