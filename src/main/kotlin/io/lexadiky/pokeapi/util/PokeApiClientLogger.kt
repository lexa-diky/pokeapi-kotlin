package io.lexadiky.pokeapi.util

/**
 * Logger of library internal http requests
 */
interface PokeApiClientLogger {

    /**
     * Whether logs should be written
     */
    val isEnabled: Boolean get() = true

    /**
     * Logs outbound requests
     */
    fun onNetworkSend(method: String, url: String) = Unit

    /**
     * Logs incoming responses
     */
    fun onNetworkReceive(method: String, statusCode: Int, url: String) = Unit
}
