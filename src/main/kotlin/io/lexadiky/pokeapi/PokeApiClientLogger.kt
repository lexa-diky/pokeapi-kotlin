package io.lexadiky.pokeapi

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
    fun onNetworkSend(method: String, url: String)

    /**
     * Logs incoming responses
     */
    fun onNetworkReceive(method: String, statusCode: Int, url: String)
}

/**
 * No operation implementation of [PokeApiClientLogger]
 */
internal class NoOpPokeApiClientLogger : PokeApiClientLogger {
    override val isEnabled: Boolean = false
    override fun onNetworkSend(method: String, url: String) = Unit
    override fun onNetworkReceive(method: String, statusCode: Int, url: String) = Unit
}
