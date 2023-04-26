package me.zodd.zoddJoin

import com.google.inject.Inject
import me.zodd.zoddJoin.config.MessageConfig
import me.zodd.zoddJoin.cache.CacheProcessor
import me.zodd.zoddJoin.listeners.JoinListener
import me.zodd.zoddJoin.listeners.QuitListener
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import org.apache.logging.log4j.Logger
import org.spongepowered.api.Sponge
import org.spongepowered.api.config.DefaultConfig
import org.spongepowered.api.event.Listener
import org.spongepowered.api.event.lifecycle.ConstructPluginEvent
import org.spongepowered.api.event.lifecycle.RefreshGameEvent
import org.spongepowered.configurate.CommentedConfigurationNode
import org.spongepowered.configurate.ConfigurateException
import org.spongepowered.configurate.reference.ConfigurationReference
import org.spongepowered.plugin.PluginContainer
import org.spongepowered.plugin.builtin.jvm.Plugin

@Plugin("zoddjoin")
class ZoddJoin @Inject internal constructor(
    private val container: PluginContainer,
    @DefaultConfig(sharedRoot = false) val reference: ConfigurationReference<CommentedConfigurationNode>,
    private val logger: Logger
) {

    private lateinit var config: MessageConfig

    @Listener
    fun pluginConstruct(event: ConstructPluginEvent) {
        loadConfig()

        Sponge.eventManager().registerListeners(this.container, JoinListener(container))
        Sponge.eventManager().registerListeners(this.container, QuitListener(container))

        processConfig()
    }

    @Listener
    fun restartEngine(event : RefreshGameEvent) {
        logger.info("Reloading and reprocessing configuration")
        loadConfig()
        processConfig()
    }

    private fun processConfig() {
        CacheProcessor(config).processConfig()
    }

    private fun loadConfig() {
        logger.info("Loading config file")
        try {
            val conf = reference.referenceTo(MessageConfig::class.java)

            reference.save()

            config = conf.get()!!

        } catch (e: ConfigurateException) {
            logger.error("Unable to load configuration", e)
        }
    }
}

operator fun String.not(): TextComponent {
    return Component.text(this)
}

