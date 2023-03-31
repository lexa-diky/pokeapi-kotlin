package io.lexadiky.pokeapi.impl

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.cache.storage.FileStorage
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.URLProtocol
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.reflect.TypeInfo
import io.lexadiky.pokeapi.network.CacheSettings
import io.lexadiky.pokeapi.network.HttpRequester
import io.lexadiky.pokeapi.util.PokeApiClientLogger
import kotlinx.serialization.json.Json
import kotlin.time.Duration

/**
 * Helper class preforming http requests using [HttpClient] from KTOR library
 */
internal class HttpRequesterImpl(
    private val logger: PokeApiClientLogger,
    private val host: String,
    private val path: String,
    private val cache: CacheSettings,
    private val timeout: Duration
) : HttpRequester {
    private val httpClient = HttpClient(CIO) {
        if (cache is CacheSettings.FileStorage) {
            install(HttpCache) {
                publicStorage(FileStorage(cache.directory))
            }
        }
        install(LoggerPlugin(logger))
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                }
            )
        }
        engine {
            requestTimeout = timeout.inWholeMilliseconds
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
    override suspend fun <T> get(type: TypeInfo, resource: String, offset: Int, limit: Int): T {
        return httpClient.get {
            url {
                host = this@HttpRequesterImpl.host
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
    override suspend fun <T> get(type: TypeInfo, resource: String, pointer: String): T {
        return httpClient.get {
            url {
                host = this@HttpRequesterImpl.host
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
    override suspend fun <T> get(type: TypeInfo, url: String): T {
        return httpClient.get(url).body(type)
    }

    companion object {

        private const val PARAM_OFFSET = "offset"
        private const val PARAM_LIMIT = "limit"
    }
}
