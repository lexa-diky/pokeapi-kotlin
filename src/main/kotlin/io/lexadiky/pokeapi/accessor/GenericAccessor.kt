package io.lexadiky.pokeapi.accessor

import io.ktor.client.call.*
import io.ktor.util.reflect.*
import io.lexadiky.pokeapi.impl.HttpRequester
import io.lexadiky.pokeapi.entity.common.HasResourcePinter
import io.lexadiky.pokeapi.entity.common.ResouceList
import io.lexadiky.pokeapi.entity.common.ResourcePointer

interface GenericAccessor<Resource> {

    suspend fun all(): Result<ResouceList<Resource>>

    suspend fun range(range: IntRange): Result<ResouceList<Resource>>

    suspend fun get(id: Int): Result<Resource>

    suspend fun get(name: String): Result<Resource>

    suspend fun get(pointer: ResourcePointer<Resource>): Result<Resource>

    suspend fun get(pointer: HasResourcePinter<Resource>): Result<Resource>
}

internal class GenericAccessorImpl<Resource>(
    private val resourceType: TypeInfo,
    private val resourceListType: TypeInfo,
    private val resourceName: String,
    private val requester: HttpRequester,
) : GenericAccessor<Resource> {

    override suspend fun all(): Result<ResouceList<Resource>> = range(ZERO_OFFSET..MAX_LIMIT)

    override suspend fun range(range: IntRange): Result<ResouceList<Resource>> = runCatching {
        requester.get(resourceListType, resourceName, offset = range.first, limit = range.last - range.first)
    }

    override suspend fun get(id: Int): Result<Resource> = runCatching {
        requester.get(resourceType, resourceName, id)
    }

    override suspend fun get(name: String): Result<Resource> = runCatching {
        requester.get(resourceType, resourceName, name)
    }

    override suspend fun get(pointer: ResourcePointer<Resource>) = get(name = pointer.name)

    override suspend fun get(pointer: HasResourcePinter<Resource>): Result<Resource> = get(pointer.pointer)

    companion object {
        private const val ZERO_OFFSET = 0
        private const val MAX_LIMIT = 500_000
    }
}

@Suppress("FunctionName")
internal inline fun <reified T> GenericAccessorImpl(resource: String, requester: HttpRequester): GenericAccessorImpl<T> {
    return GenericAccessorImpl(
        resourceType = typeInfo<T>(),
        resourceListType = typeInfo<ResouceList<T>>(),
        resourceName = resource,
        requester = requester
    )
}
