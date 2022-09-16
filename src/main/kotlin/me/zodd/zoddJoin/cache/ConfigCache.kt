package me.zodd.zoddJoin.cache

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import me.zodd.zoddJoin.config.*
import me.zodd.zoddJoin.parser.ActionHolder

object ConfigCache {
    val joinFormatCache: Cache<String, FormatHolder> = Caffeine.newBuilder().build()

    val quitFormatCache: Cache<String, FormatHolder> = Caffeine.newBuilder().build()

    val motdCache: Cache<String, MotdHolder> = Caffeine.newBuilder().build()

    val firstJoinMessageCache = mutableListOf<ActionHolder>()

    fun <K,V: Holder> Cache<K,V>.values() = this.asMap().values
}

data class FormatHolder(
    val actions: List<ActionHolder>,
    override val settings: FormatSettings,
) : Holder

data class MotdHolder(
    val action: List<ActionHolder>,
    override val settings: MotdSettings
) : Holder

interface Holder {
    val settings: ActionValues
}