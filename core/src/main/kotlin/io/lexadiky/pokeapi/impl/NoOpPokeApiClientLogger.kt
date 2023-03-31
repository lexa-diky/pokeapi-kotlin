package io.lexadiky.pokeapi.impl

import io.lexadiky.pokeapi.util.PokeApiClientLogger

/**
 * No operation implementation of [PokeApiClientLogger]
 */
internal class NoOpPokeApiClientLogger : PokeApiClientLogger {
    override val isEnabled: Boolean = false
    override fun onNetworkSend(method: String, url: String) = Unit
    override fun onNetworkReceive(method: String, statusCode: Int, url: String) = Unit
}
