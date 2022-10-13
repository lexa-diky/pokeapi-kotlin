package io.lexadiky.pokeapi.impl

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.cache.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.util.reflect.*
import io.lexadiky.pokeapi.PokeApiClientLogger
import kotlinx.serialization.json.Json

/**
 * Helper class preforming http requests using [HttpClient] from KTOR library
 */
internal class HttpRequester(
    private val logger: PokeApiClientLogger,
    private val host: String,
    private val path: String,
    private val useCache: Boolean,
) {
    private val httpClient = HttpClient(CIO) {
        if (useCache) {
            install(HttpCache)
        }
        install(LoggerPlugin(logger))
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                }
            )
        }
    }

    /**
     * Retrieves resource list
     *
     * @param type type of list to retrieve
     * @param resource resource to retrieve
     * @param offset offset query value
     * @param limit limit query value
     */
    suspend fun <T> get(type: TypeInfo, resource: String, offset: Int, limit: Int): T {
        return httpClient.get {
            url {
                host = this@HttpRequester.host
                protocol = URLProtocol.HTTPS
                path(path, resource)
                parameter(PARAM_OFFSET, offset)
                parameter(PARAM_LIMIT, limit)
            }
        }.body(type)
    }

    /**
     * Retrieves resource by pointing value
     *
     * @param type type of list to retrieve
     * @param resource resource to retrieve
     * @param pointer either name or id of resource
     */
    suspend fun <T> get(type: TypeInfo, resource: String, pointer: Any): T {
        return httpClient.get {
            url {
                host = this@HttpRequester.host
                protocol = URLProtocol.HTTPS
                path(path, resource, pointer.toString())
            }
        }.body(type)
    }

    /**
     * Retrieves resource by full url
     *
     * @param type type of list to retrieve
     * @param url to retrieve, [host] and [path] will be ignored
     */
    suspend fun <T> get(type: TypeInfo, url: String): T {
        return httpClient.get(url).body(type)
    }

    companion object {

        private const val PARAM_OFFSET = "offset"
        private const val PARAM_LIMIT = "limit"
    }
}