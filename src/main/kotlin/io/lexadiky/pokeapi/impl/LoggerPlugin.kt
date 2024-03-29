package io.lexadiky.pokeapi.impl

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpClientPlugin
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.plugin
import io.ktor.util.AttributeKey
import io.lexadiky.pokeapi.util.PokeApiClientLogger

/**
 * Plugin performing logging into [PokeApiClientLogger]
 */
internal class LoggerPlugin(private val logger: PokeApiClientLogger) : HttpClientPlugin<LoggerPlugin, LoggerPlugin> {

    override val key: AttributeKey<LoggerPlugin> = AttributeKey(LOGGER_PLUGIN_KEY)

    override fun prepare(block: LoggerPlugin.() -> Unit): LoggerPlugin {
        return this.apply(block)
    }

    override fun install(plugin: LoggerPlugin, scope: HttpClient) {
        scope.plugin(HttpSend).intercept { request ->
            if (logger.isEnabled) {
                logger.onNetworkSend(
                    method = request.method.value,
                    url = request.url.buildString()
                )
            }
            val originalCall = execute(request)
            if (logger.isEnabled) {
                logger.onNetworkReceive(
                    method = originalCall.request.method.value,
                    statusCode = originalCall.response.status.value,
                    url = originalCall.request.url.toString()
                )
            }
            originalCall
        }
    }

    companion object {

        private const val LOGGER_PLUGIN_KEY = "POKEAPI_LOGGER_PLUGIN"
    }
}
