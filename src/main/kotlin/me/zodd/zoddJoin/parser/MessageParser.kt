package me.zodd.zoddJoin.parser

import me.zodd.zoddJoin.actions.*
import me.zodd.zoddJoin.actions.services.ActionService

class MessageParser {

    fun parseMessages(messages: List<String>): List<ActionHolder> {
        val list = messages.map {
            parse(it)
        }
        return list
    }

    private val regex = """(\w*)(=?\d*)(:)""".toRegex()

    @kotlin.jvm.Throws(IllegalArgumentException::class)
    private fun parse(string: String): ActionHolder {
        if (!string.contains(regex)) throw IllegalArgumentException("Invalid Syntax, $string")

        val splitString = string.split(":", limit = 2)

        val tokenString = splitString[0].trim().split(" ")
        val contentString = splitString[1].trim()


        val actions = tokenString.filterNot { it.contains("=") }.map { parseAction(it) }

        val modifiers = tokenString.filter { it.contains("=") }.map { parseModifier(it) }


        val pair = ActionModifierPair(
            actions,
            modifiers
        )

        return ActionHolder(
            pair,
            contentString
        )
    }

    private fun parseAction(action: String) : Action {
        return ActionService.serviceSet.firstOrNull() { it.id == action } ?: throw IllegalArgumentException("Invalid Action, $action")
    }

    @kotlin.jvm.Throws(IllegalArgumentException::class)
    private fun parseModifier(modifier: String) : Modifier {
        val split = modifier.split("=")
        val modifierId = split[0]
        val value = split[1].toInt()
        return when(modifierId) {
            "CHANCE" -> {
                Chance(value)
            }
            "DELAY" -> {
                Delay(value)
            }
            else -> {
                throw IllegalArgumentException("Invalid Modifier, $modifierId")
            }
        }
    }
}
