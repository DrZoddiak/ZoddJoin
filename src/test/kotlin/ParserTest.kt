import me.zodd.zoddJoin.actions.ActionHolder
import me.zodd.zoddJoin.actions.ActionModifierPair
import me.zodd.zoddJoin.actions.Chance
import me.zodd.zoddJoin.actions.Delay
import me.zodd.zoddJoin.actions.broadcast.BroadcastAction
import me.zodd.zoddJoin.actions.player.MessageAction
import me.zodd.zoddJoin.parser.*
import org.junit.Assert.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals

class ParserTest {

    private val parser = MessageParser()

    @Test
    fun conversionTest() {
        mapTestSet.forEach {
            val parsed = parser.parseMessages(it.key)
            assertEquals(it.value, parsed)
        }
    }

    @Test
    fun invalidSyntaxTest() {
        val invalidSyntax = listOf(
            "BROADCAST <yellow>[<green>+<yellow>]<green> <name>"
        )
        illegalArgumentTest(invalidSyntax)
    }

    @Test
    fun invalidModifierTest() {
        val invalidModifier = listOf(
            "MESSAGE NOTEXIST=50 : Nice try"
        )
        illegalArgumentTest(invalidModifier)
    }

    @Test
    fun invalidActionTest() {
        val invalidAction = listOf(
            "MSSG DELAY=1 CHANCE=50:There's a 50% chance you'll see this!"
        )
        illegalArgumentTest(invalidAction)
    }

    private val validTestingSet = listOf(
        "BROADCAST : <yellow>[<green>+<yellow>]<green> <name>",
        "MESSAGE: <green>Welcome back!",
        "MESSAGE DELAY=1 CHANCE=50:There's a 50% chance you'll see this!",
        "BROADCAST DELAY=10: <red>Don't forget to <rainbow>vote!"
    )

    private val mapTestSet = mapOf(
        validTestingSet to listOf(
            ActionHolder(
                ActionModifierPair(
                    listOf(BroadcastAction()),
                    listOf()
                ), "<yellow>[<green>+<yellow>]<green> <name>"
            ),
            ActionHolder(
                ActionModifierPair(
                    listOf(MessageAction()),
                    listOf()
                ), "<green>Welcome back!"
            ),
            ActionHolder(
                ActionModifierPair(
                    listOf(MessageAction()),
                    listOf(Delay(1), Chance(50))
                ), "There's a 50% chance you'll see this!"
            ),
            ActionHolder(
                ActionModifierPair(
                    listOf(BroadcastAction()),
                    listOf(Delay(10))
                ), "<red>Don't forget to <rainbow>vote!"
            )
        ).toMutableList()
    )

    private fun illegalArgumentTest(list: List<String>) {
        assertThrows(IllegalArgumentException::class.java) {
            parser.parseMessages(list)
        }
    }
}