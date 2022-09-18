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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Action) return false

        if (this.id == other.id)
            return true

        return false
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}