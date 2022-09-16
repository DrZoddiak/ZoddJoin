package me.zodd.zoddJoin.actions

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import org.spongepowered.api.entity.living.player.server.ServerPlayer

interface Action {
    val id: String
    fun run(player: ServerPlayer,data: String,isRunnable : Boolean = true)
}

abstract class AbstractAction : Action {

    private val mm = MiniMessage.miniMessage()

    fun deserialize(data: String, name: String = ""): Component {
        if (name.isEmpty()) {
            return mm.deserialize(data)
        }
        return mm.deserialize(data, Placeholder.unparsed("name", name))
    }

    override fun toString(): String {
        return this.id
    }
}