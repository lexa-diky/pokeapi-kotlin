package io.lexadiky.pokeapi

import io.lexadiky.pokeapi.accessor.PokeApiAbilityResourceAccessor
import io.lexadiky.pokeapi.accessor.PokeApiAbilityResourceAccessorImpl
import io.lexadiky.pokeapi.accessor.PokeApiPokemonResourceAccessor
import io.lexadiky.pokeapi.accessor.PokeApiPokemonResourceAccessorImpl
import io.lexadiky.pokeapi.accessor.PokeApiTypeResourceAccessor
import io.lexadiky.pokeapi.accessor.PokeApiTypeResourceAccessorImpl
import io.lexadiky.pokeapi.accessor.getAll
import io.lexadiky.pokeapi.impl.HttpRequester
import kotlinx.coroutines.runBlocking

interface PokeApiClient {

    val pokemon: PokeApiPokemonResourceAccessor

    val type: PokeApiTypeResourceAccessor

    val ability: PokeApiAbilityResourceAccessor

    suspend fun <T> use(computation: suspend PokeApiFluidContext.() -> T): Result<T>
}

internal class PokeApiClientImpl(host: String, path: String) : PokeApiClient {

    private val requester: HttpRequester = HttpRequester(host, path)
    private val fluidContext: PokeApiFluidContext = PokeApiFluidContextImpl(requester, this)

    override val pokemon: PokeApiPokemonResourceAccessor = PokeApiPokemonResourceAccessorImpl(requester)

    override val type: PokeApiTypeResourceAccessor = PokeApiTypeResourceAccessorImpl(requester)

    override val ability: PokeApiAbilityResourceAccessor = PokeApiAbilityResourceAccessorImpl(requester)

    override suspend fun <T> use(computation: suspend PokeApiFluidContext.() -> T): Result<T> {
        return runCatching { fluidContext.computation() }
    }
}
