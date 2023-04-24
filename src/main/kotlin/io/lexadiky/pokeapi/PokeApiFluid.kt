package io.lexadiky.pokeapi

import io.ktor.util.reflect.TypeInfo
import io.ktor.util.reflect.typeInfo
import io.lexadiky.pokeapi.entity.common.ResourceList
import io.lexadiky.pokeapi.entity.common.ResourcePointer
import io.lexadiky.pokeapi.impl.PokeApiClientImpl
import io.lexadiky.pokeapi.network.HttpRequester

/**
 * Marker interface for Fluid API.
 *
 * Should not be overridden manually in any case.
 */
interface PokeApiFluidContext : PokeApiClient

/**
 * Implementation of [PokeApiFluidContext] with [PokeApiClient] under the hood.
 */
@PublishedApi
internal class PokeApiFluidContextImpl(private val requester: HttpRequester, pokeApiClientImpl: PokeApiClientImpl) :
    PokeApiFluidContext,
    PokeApiClient by pokeApiClientImpl {

    suspend fun <T> internalGet(type: TypeInfo, resourcePointer: ResourcePointer<T>): T {
        return requester.get(type, resourcePointer.url)
    }
}

context(PokeApiFluidContext)
suspend inline fun <reified T> ResourcePointer<T>.get(): T {
    val pokeApiFluidContextImpl = this@PokeApiFluidContext as? PokeApiFluidContextImpl
        ?: error("only ${PokeApiFluidContextImpl::class.java} supported as fluid context implementation, got ${this@PokeApiFluidContext::class.java}")

    return pokeApiFluidContextImpl.internalGet(
        type = typeInfo<T>(),
        resourcePointer = this
    )
}

context(PokeApiFluidContext)
fun <T> Result<ResourceList<T>>.get(): ResourceList<T> {
    return this.getOrThrow() // safe, because functions with PokeApiFluidContext as context are guaranteed to catch all exceptions
}
