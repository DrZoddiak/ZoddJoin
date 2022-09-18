package me.zodd.zoddJoin.actions

import kotlin.random.Random

data class ActionHolder(
    val pair: ActionModifierPair,
    val content: String
) {
    private val chance
        get() = this.pair.modifier.firstOrNull { it.id == Chance().id }?.value ?: 100

    val delay: Int
        get() = this.pair.modifier.firstOrNull { it.id == Delay().id }?.value ?: 0

    val isRunnable
        get() = hasChance()

    private fun hasChance(): Boolean {
        val rand = Random.nextInt(100) + 1
        return rand <= chance
    }

    override fun toString(): String {
        return """
            ACTIONS : ${pair.action.joinToString()}
            MODIFIERS: ${pair.modifier.joinToString()}
            CONTENT : $content
        """.trimIndent()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ActionHolder) return false

        if (this.pair == other.pair &&
            this.content == other.content
        )
        return true

        println("Types aren't equal")
        return false
    }

    override fun hashCode(): Int {
        var result = pair.hashCode()
        result = 31 * result + content.hashCode()
        return result
    }
}

data class ActionModifierPair(
    val action: List<Action>,
    val modifier: List<Modifier>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ActionModifierPair) return false

        if (action == other.action &&
            this.modifier == other.modifier
        )
            return true

        return false
    }

    override fun hashCode(): Int {
        var result = action.hashCode()
        result = 31 * result + modifier.hashCode()
        return result
    }
}

interface Modifier {
    val id: String
    val value: Int
}

abstract class AbstractModifier : Modifier {
    override fun toString(): String {
        return "$id:$value"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Modifier) return false

        if (this.id == other.id && this.value == other.value) return true

        return false
    }

    override fun hashCode(): Int {
        var result = value
        result = 31 * result + id.hashCode()
        return result
    }
}

class Chance(override val value: Int = 100) : AbstractModifier() {
    override val id: String = "CHANCE"
}

class Delay(override val value: Int = 0) : AbstractModifier() {
    override val id: String = "DELAY"
}