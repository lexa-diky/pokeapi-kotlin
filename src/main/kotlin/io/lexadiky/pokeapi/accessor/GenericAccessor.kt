package io.lexadiky.pokeapi.accessor

import io.ktor.client.call.*
import io.ktor.util.reflect.*
import io.lexadiky.pokeapi.impl.HttpRequester
import io.lexadiky.pokeapi.entity.common.HasResourcePointer
import io.lexadiky.pokeapi.entity.common.PagingPointer
import io.lexadiky.pokeapi.entity.common.ResourceList
import io.lexadiky.pokeapi.entity.common.ResourcePointer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

interface GenericAccessor<Resource> {

    suspend fun all(): Result<ResourceList<Resource>>

    suspend fun range(range: IntRange): Result<ResourceList<Resource>>

    suspend fun get(id: Int): Result<Resource>

    suspend fun get(name: String): Result<Resource>

    suspend fun get(pointer: ResourcePointer<Resource>): Result<Resource>

    suspend fun get(pointer: HasResourcePointer<Resource>): Result<Resource>

    fun pages(size: Int): Pages<Resource>

    interface Pages<Resource> {

        suspend fun first(): Result<ResourceList<Resource>>

        suspend fun get(pointer: PagingPointer<Resource>): Result<ResourceList<Resource>>
    }
}

suspend fun <T> GenericAccessor<T>.getAll(): Flow<Result<T>> {
    val allItems = all()
    return if (allItems.isFailure) {
        flowOf(Result.failure(allItems.exceptionOrNull()!!))
    } else {
        flow {
            allItems.getOrThrow().forEach {
                emit(get(it))
            }
        }
    }
}

internal class GenericAccessorImpl<Resource>(
    private val resourceType: TypeInfo,
    private val resourceListType: TypeInfo,
    private val resourceName: String,
    private val requester: HttpRequester,
) : GenericAccessor<Resource> {

    override suspend fun all(): Result<ResourceList<Resource>> = runCatching {
        val result = range(ZERO_OFFSET..MAX_LIMIT).getOrThrow()
        require(result.next == null && result.previous == null) {
            "Was not able to fetch all resources in one go, please paginate with ${::range} method"
        }
        result
    }

    override suspend fun range(range: IntRange): Result<ResourceList<Resource>> = runCatching {
        requester.get(resourceListType, resourceName, offset = range.first, limit = range.last - range.first)
    }

    override suspend fun get(id: Int): Result<Resource> = runCatching {
        requester.get(resourceType, resourceName, id)
    }

    override suspend fun get(name: String): Result<Resource> = runCatching {
        requester.get(resourceType, resourceName, name)
    }

    override suspend fun get(pointer: ResourcePointer<Resource>) = get(name = pointer.name)

    override suspend fun get(pointer: HasResourcePointer<Resource>): Result<Resource> = get(pointer.pointer)

    override fun pages(size: Int): GenericAccessor.Pages<Resource> {
        return PagesImpl(size)
    }

    @Suppress("RemoveExplicitTypeArguments")
    inner class PagesImpl(private val size: Int): GenericAccessor.Pages<Resource> {

        override suspend fun first(): Result<ResourceList<Resource>> = runCatching {
            requester.get<ResourceList<Resource>>(resourceListType, resourceName, offset = 0, limit = size)
        }

        override suspend fun get(pointer: PagingPointer<Resource>): Result<ResourceList<Resource>> = runCatching {
            requester.get<ResourceList<Resource>>(resourceListType, pointer.url)
        }
    }

    companion object {
        private const val ZERO_OFFSET = 0
        private const val MAX_LIMIT = 500_000
    }
}

@Suppress("FunctionName")
internal inline fun <reified T> GenericAccessorImpl(resource: String, requester: HttpRequester): GenericAccessorImpl<T> {
    return GenericAccessorImpl(
        resourceType = typeInfo<T>(),
        resourceListType = typeInfo<ResourceList<T>>(),
        resourceName = resource,
        requester = requester
    )
}
