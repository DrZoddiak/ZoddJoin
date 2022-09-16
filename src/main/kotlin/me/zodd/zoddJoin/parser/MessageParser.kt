package me.zodd.zoddJoin.parser

import me.zodd.core.logger
import me.zodd.zoddJoin.actions.Action
import me.zodd.zoddJoin.actions.player.MessageAction
import me.zodd.zoddJoin.actions.services.ActionService
import kotlin.jvm.optionals.getOrElse

class MessageParser {

    fun parseMessages(messages: List<String>): List<ActionHolder> {
        val list = messages.map {
            parse(it)
        }
        return list
    }

    private val regex = """(\w*)(=?\d*)(:)""".toRegex()

    private fun parse(string: String): ActionHolder {
        if (!string.contains(regex)) throw IllegalArgumentException("Invalid configurations")

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

    @OptIn(ExperimentalStdlibApi::class)
    private fun parseAction(action: String) : Action {
        val services = ActionService.serviceSet.stream().filter {
            it.id == action
        }.findFirst().getOrElse { MessageAction() }
        return services
    }

    private fun parseModifier(modifier: String) : Modifier {
        val split = modifier.split("=")
        val value = split[1].toInt()
        return when(split[0]) {
            "CHANCE" -> {
                Chance(value)
            }
            "DELAY" -> {
                Delay(value)
            }
            else -> {
                throw IllegalArgumentException("Invalid Arg")
            }
        }
    }
}
