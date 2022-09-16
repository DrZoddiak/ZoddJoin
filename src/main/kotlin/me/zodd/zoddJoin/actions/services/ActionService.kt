package me.zodd.zoddJoin.actions.services

import me.zodd.zoddJoin.actions.Action
import java.util.*
import java.util.stream.Collectors

object ActionService {
    val serviceSet: Set<Action> by lazy { loadServices() }

    private inline fun <reified S : Action> loadServices(): Set<S> {
        return ServiceLoader.load(S::class.java).stream()
            .map { it.get() }
            .collect(Collectors.toSet())
    }
}