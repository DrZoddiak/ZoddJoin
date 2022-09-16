package me.zodd.zoddJoin.config

import org.spongepowered.configurate.objectmapping.ConfigSerializable
import org.spongepowered.configurate.objectmapping.meta.Comment

@ConfigSerializable
data class MessageConfig(
    @field:Comment("Actions to send if it is a players first time on the server")
    val firstJoinMessage: List<String> = listOf(
        "MESSAGE: <yellow> Welcome to the server <name>!",
        "BROADCAST : <name> has joined for the first time! Give them a welcome!"
    ),
    val motds: Map<String,Motds> = mapOf("default" to Motds()),
    val formats: Map<String,Formats> = mapOf("default" to Formats(),"extra" to Formats(1, permission = "example.permission.node"))
)

@ConfigSerializable
data class Motds(
    @field:Comment("Priority to show Motds, Higher means higher likelihood")
    override val priority: Int = 1,
    @field:Comment("Delay is in seconds")
    override val delay: Int = 0,
    @field:Comment("Permission to check")
    override val permission: String = "",
    val actions: List<String> = listOf(
        "MESSAGE : <YELLOW>__________________________________",
        "MESSAGE : <RAINBOW>Welcome to the server <green><name>",
        "MESSAGE : <YELLOW>__________________________________"
    )
) : MotdOptions

data class MotdSettings(
    override val priority: Int = 1,
    override val delay: Int = 0,
    override val permission: String = ""
) : MotdOptions

interface MotdOptions : ActionValues {
    val delay : Int
}

@ConfigSerializable
data class Formats(
    @field:Comment("Priority to show Formats, Higher means higher likelihood")
    override val priority: Int = 2,
    @field:Comment("3 values allowed; NORMAL, RANDOM, VANISH")
    override val type: String = "NORMAL",
    val join: List<String> = listOf(
        "BROADCAST : <yellow>[<green>+<yellow>]<green> <name>",
        "MESSAGE: <green>Welcome back!",
        "MESSAGE DELAY=1 CHANCE=50:There's a 50% chance you'll see this!",
        "BROADCAST DELAY=10: <red>Don't forget to <rainbow>vote!"
    ),
    val quit: List<String> = listOf(
        "BROADCAST:<yellow>[<red>-<yellow>]<green> <name>"
    ),
    @field:Comment("Permission to check")
    override val permission: String = ""
) : FormatOptions

data class FormatSettings(
    override val type: String = "NORMAL",
    override val priority: Int = 1,
    override val permission: String = "zoddjoin.base.default"
) : FormatOptions

interface FormatOptions : ActionValues {
    val type : String
}

interface ActionValues {
    val permission : String
    val priority : Int
}
