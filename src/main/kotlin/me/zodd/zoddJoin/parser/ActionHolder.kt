package me.zodd.zoddJoin.parser

import me.zodd.zoddJoin.actions.Action
import kotlin.random.Random

data class ActionHolder(
    val pair: ActionModifierPair,
    val content: String
) {
    private val chance
        get() = this.pair.modifier.firstOrNull { it.id == Chance().id }?.value ?: 100

    var delay: Int = 0
        get() = this.pair.modifier.firstOrNull { it.id == Delay().id }?.value ?: 0
        set(value) {
            field += value
        }

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
}

data class ActionModifierPair(
    val action: List<Action>,
    val modifier: List<Modifier>
)

interface Modifier {
    val id: String
    val value: Int
}

class Chance(override val value: Int = 100) : Modifier {
    override val id: String = "CHANCE"
}

class Delay(override val value: Int = 0) : Modifier {
    override val id: String = "DELAY"
}