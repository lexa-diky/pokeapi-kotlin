package io.lexadiky.pokeapi.network

import io.ktor.util.reflect.TypeInfo

interface HttpRequester {

    /**
     * Retrieves resource list
     *
     * @param type type of list to retrieve
     * @param resource resource to retrieve
     * @param offset offset query value
     * @param limit limit query value
     */
    suspend fun <T> get(type: TypeInfo, resource: String, offset: Int, limit: Int): T

    /**
     * Retrieves resource by pointing value
     *
     * @param type type of list to retrieve
     * @param resource resource to retrieve
     * @param pointer either name or id of resource
     */
    suspend fun <T> get(type: TypeInfo, resource: String, pointer: String): T

    /**
     * Retrieves resource by full url
     *
     * @param type type of list to retrieve
     * @param url to retrieve, [host] and [path] will be ignored
     */
    suspend fun <T> get(type: TypeInfo, url: String): T
}
