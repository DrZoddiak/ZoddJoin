package me.zodd.zoddJoin.cache

import me.zodd.zoddJoin.config.FormatSettings
import me.zodd.zoddJoin.config.MessageConfig
import me.zodd.zoddJoin.config.MotdSettings
import me.zodd.zoddJoin.parser.MessageParser

class CacheProcessor(private val config: MessageConfig) {

    private val messageParser = MessageParser()

    fun processConfig() {
        processFirstJoin()
        processFormats()
        processMotds()
    }

    private fun processFirstJoin() {
        val fjToken = messageParser.parseMessages(config.firstJoinMessage)

        ConfigCache.firstJoinMessageCache.addAll(fjToken)
    }

    private fun processFormats() {
        config.formats.forEach {
            val format = it.value

            val type = format.type
            val priority = format.priority
            val permission = format.permission

            val joinToken = messageParser.parseMessages(format.join)
            val quitToken = messageParser.parseMessages(format.quit)

            val settings = FormatSettings(type, priority, permission)

            val joinFormat = FormatHolder(joinToken, settings)
            val quitFormat = FormatHolder(quitToken, settings)

            ConfigCache.joinFormatCache.put(it.key, joinFormat)
            ConfigCache.quitFormatCache.put(it.key, quitFormat)
        }
    }

    private fun processMotds() {
        config.motds.forEach {
            val format = it.value

            val motdTokens = messageParser.parseMessages(format.actions)

            val settings = MotdSettings(format.priority, format.delay, format.permission)

            val holder = MotdHolder(motdTokens, settings)

            ConfigCache.motdCache.put(it.key, holder)
        }
    }
}

