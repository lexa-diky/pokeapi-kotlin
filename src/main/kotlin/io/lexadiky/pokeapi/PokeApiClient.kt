package io.lexadiky.pokeapi

import io.lexadiky.pokeapi.accessor.PokeApiAbilityResourceAccessor
import io.lexadiky.pokeapi.accessor.PokeApiAbilityResourceAccessorImpl
import io.lexadiky.pokeapi.accessor.PokeApiLanguageResourceAccessor
import io.lexadiky.pokeapi.accessor.PokeApiLanguageResourceAccessorImpl
import io.lexadiky.pokeapi.accessor.PokeApiPokemonColorResourceAccessor
import io.lexadiky.pokeapi.accessor.PokeApiPokemonColorResourceAccessorImpl
import io.lexadiky.pokeapi.accessor.PokeApiPokemonResourceAccessor
import io.lexadiky.pokeapi.accessor.PokeApiPokemonResourceAccessorImpl
import io.lexadiky.pokeapi.accessor.PokeApiPokemonSpeciesResourceAccessor
import io.lexadiky.pokeapi.accessor.PokeApiPokemonSpeciesResourceAccessorImpl
import io.lexadiky.pokeapi.accessor.PokeApiTypeResourceAccessor
import io.lexadiky.pokeapi.accessor.PokeApiTypeResourceAccessorImpl
import io.lexadiky.pokeapi.accessor.PokeApiVersionResourceAccessor
import io.lexadiky.pokeapi.accessor.PokeApiVersionResourceAccessorImpl
import io.lexadiky.pokeapi.impl.HttpRequester
import kotlinx.coroutines.runBlocking

/**
 * Entry point for PokeApi.
 *
 * Use either properties like [pokemon] to access normal REST style requests or [use] for Fluid API
 */
interface PokeApiClient {

    /**
     * [pokemon](https://pokeapi.co/docs/v2#pokemon) resource
     */
    val pokemon: PokeApiPokemonResourceAccessor

    /**
     * [type](https://pokeapi.co/docs/v2#type) resource
     */
    val type: PokeApiTypeResourceAccessor

    /**
     * [ability](https://pokeapi.co/docs/v2#ability) resource
     */
    val ability: PokeApiAbilityResourceAccessor

    /**
     * [version](https://pokeapi.co/docs/v2#version) resource
     */
    val version: PokeApiVersionResourceAccessor

    /**
     * [languages](https://pokeapi.co/docs/v2#languages) resource
     */
    val language: PokeApiLanguageResourceAccessor

    /**
     * [pokemon-colors](https://pokeapi.co/docs/v2#pokemon-colors) resource
     */
    val pokemonColor: PokeApiPokemonColorResourceAccessor

    /**
     * [pokemon-species](https://pokeapi.co/docs/v2#pokemon-species) resource
     */
    val pokemonSpecies: PokeApiPokemonSpeciesResourceAccessor

    /**
     * Entry point for Fluid API
     */
    suspend fun <T> use(computation: suspend PokeApiFluidContext.() -> T): Result<T>
}

/**
 * Default [PokeApiClient] implementation using KTOR library
 */
internal class PokeApiClientImpl(logger: PokeApiClientLogger, host: String, path: String, useCache: Boolean) : PokeApiClient {

    private val requester: HttpRequester = HttpRequester(logger, host, path, useCache)
    private val fluidContext: PokeApiFluidContext = PokeApiFluidContextImpl(requester, this)

    override val pokemon: PokeApiPokemonResourceAccessor = PokeApiPokemonResourceAccessorImpl(requester)
    override val type: PokeApiTypeResourceAccessor = PokeApiTypeResourceAccessorImpl(requester)
    override val ability: PokeApiAbilityResourceAccessor = PokeApiAbilityResourceAccessorImpl(requester)
    override val version: PokeApiVersionResourceAccessor = PokeApiVersionResourceAccessorImpl(requester)
    override val language: PokeApiLanguageResourceAccessor = PokeApiLanguageResourceAccessorImpl(requester)
    override val pokemonColor: PokeApiPokemonColorResourceAccessor = PokeApiPokemonColorResourceAccessorImpl(requester)
    override val pokemonSpecies: PokeApiPokemonSpeciesResourceAccessor = PokeApiPokemonSpeciesResourceAccessorImpl(requester)

    override suspend fun <T> use(computation: suspend PokeApiFluidContext.() -> T): Result<T> {
        return runCatching { fluidContext.computation() }
    }
}

/**
 * Builder for [PokeApiClient]
 */
class PokeApiClientBuilder internal constructor() {
    /**
     * Host of PokeApi instance
     */
    var host: String = "pokeapi.co"

    /**
     * Path to API endpoint
     */
    var path: String = "api/v2"

    /**
     * If true API responses will be cached
     */
    var useCache: Boolean = true

    /**
     * [PokeApiClientLogger] implementation to log http requests made by library. No logging by default
     */
    var logger: PokeApiClientLogger = NoOpPokeApiClientLogger()

    /**
     * Builds [PokeApiClient]
     */
    internal fun build(): PokeApiClient = PokeApiClientImpl(
        logger = logger,
        host = host,
        path = path,
        useCache = useCache
    )
}

/**
 * Primary library entry point. Creates [PokeApiClient] with settings in [builder]
 *
 * @param builder builder lambda, if not passed default settings will be used
 * @return [PokeApiClient]
 *
 * @sample io.lexadiky.pokeapi.PokeApiClientSample
 */
fun PokeApiClient(builder: (PokeApiClientBuilder.() -> Unit)? = null): PokeApiClient {
    if (builder == null) {
        return PokeApiClientBuilder().build()
    }
    return PokeApiClientBuilder().apply(builder)
        .build()
}
