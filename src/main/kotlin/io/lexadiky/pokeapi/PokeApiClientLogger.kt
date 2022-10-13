package io.lexadiky.pokeapi

import io.ktor.http.*

interface PokeApiClientLogger {

    val isEnabled: Boolean get() = true

    fun onNetworkSend(method: String, url: String)

    fun onNetworkReceive(method: String, statusCode: Int, url: String)
}

class NoOpPokeApiClientLogger : PokeApiClientLogger {
    override val isEnabled: Boolean = false
    override fun onNetworkSend(method: String, url: String) = Unit
    override fun onNetworkReceive(method: String, statusCode: Int, url: String) = Unit
}
