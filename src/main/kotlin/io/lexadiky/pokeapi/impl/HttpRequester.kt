package io.lexadiky.pokeapi.impl

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.util.reflect.*
import kotlinx.serialization.json.Json

internal class HttpRequester(private val host: String, private val path: String) {
    private val httpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                }
            )
        }
    }

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

    suspend fun <T> get(type: TypeInfo, resource: String, pointer: Any): T {
        return httpClient.get {
            url {
                host = this@HttpRequester.host
                protocol = URLProtocol.HTTPS
                path(path, resource, pointer.toString())
            }
        }.body(type)
    }

    suspend fun <T> get(type: TypeInfo, url: String): T {
        return httpClient.get(url).body(type)
    }

    companion object {

        private const val PARAM_OFFSET = "offset"
        private const val PARAM_LIMIT = "limit"
    }
}