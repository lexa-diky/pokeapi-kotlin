package io.lexadiky.pokeapi

import io.lexadiky.pokeapi.accessor.PokeApiAbilityResourceAccessor
import io.lexadiky.pokeapi.accessor.PokeApiAbilityResourceAccessorImpl
import io.lexadiky.pokeapi.accessor.PokeApiLanguageResourceAccessor
import io.lexadiky.pokeapi.accessor.PokeApiLanguageResourceAccessorImpl
import io.lexadiky.pokeapi.accessor.PokeApiPokemonColorResourceAccessor
import io.lexadiky.pokeapi.accessor.PokeApiPokemonColorResourceAccessorImpl
import io.lexadiky.pokeapi.accessor.PokeApiPokemonResourceAccessor
import io.lexadiky.pokeapi.accessor.PokeApiPokemonResourceAccessorImpl
import io.lexadiky.pokeapi.accessor.PokeApiTypeResourceAccessor
import io.lexadiky.pokeapi.accessor.PokeApiTypeResourceAccessorImpl
import io.lexadiky.pokeapi.accessor.PokeApiVersionResourceAccessor
import io.lexadiky.pokeapi.accessor.PokeApiVersionResourceAccessorImpl
import io.lexadiky.pokeapi.accessor.getAll
import io.lexadiky.pokeapi.impl.HttpRequester
import kotlinx.coroutines.runBlocking

interface PokeApiClient {

    val pokemon: PokeApiPokemonResourceAccessor
    val type: PokeApiTypeResourceAccessor
    val ability: PokeApiAbilityResourceAccessor
    val version: PokeApiVersionResourceAccessor
    val language: PokeApiLanguageResourceAccessor
    val pokemonColor: PokeApiPokemonColorResourceAccessor

    suspend fun <T> use(computation: suspend PokeApiFluidContext.() -> T): Result<T>
}

internal class PokeApiClientImpl(host: String, path: String, useCache: Boolean) : PokeApiClient {

    private val requester: HttpRequester = HttpRequester(host, path, useCache)
    private val fluidContext: PokeApiFluidContext = PokeApiFluidContextImpl(requester, this)

    override val pokemon: PokeApiPokemonResourceAccessor = PokeApiPokemonResourceAccessorImpl(requester)
    override val type: PokeApiTypeResourceAccessor = PokeApiTypeResourceAccessorImpl(requester)
    override val ability: PokeApiAbilityResourceAccessor = PokeApiAbilityResourceAccessorImpl(requester)
    override val version: PokeApiVersionResourceAccessor = PokeApiVersionResourceAccessorImpl(requester)
    override val language: PokeApiLanguageResourceAccessor = PokeApiLanguageResourceAccessorImpl(requester)
    override val pokemonColor: PokeApiPokemonColorResourceAccessor = PokeApiPokemonColorResourceAccessorImpl(requester)

    override suspend fun <T> use(computation: suspend PokeApiFluidContext.() -> T): Result<T> {
        return runCatching { fluidContext.computation() }
    }
}

class PokeApiClientBuilder internal constructor() {
    var host: String = "pokeapi.co"
    var path: String = "api/v2"
    var useCache: Boolean = true

    internal fun build(): PokeApiClient = PokeApiClientImpl(
        host = host,
        path = path,
        useCache = useCache
    )
}

fun PokeApiClient(builder: PokeApiClientBuilder.() -> Unit): PokeApiClient {
    return PokeApiClientBuilder().apply(builder)
        .build()
}
