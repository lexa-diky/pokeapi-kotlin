package io.lexadiky.pokeapi

import io.ktor.client.plugins.cache.storage.*
import io.lexadiky.pokeapi.accessor.PokeApiAbilityResourceAccessor
import io.lexadiky.pokeapi.accessor.PokeApiAbilityResourceAccessorImpl
import io.lexadiky.pokeapi.accessor.PokeApiCharacteristicResourceAccessor
import io.lexadiky.pokeapi.accessor.PokeApiCharacteristicResourceAccessorImpl
import io.lexadiky.pokeapi.accessor.PokeApiEggGroupResourceAccessor
import io.lexadiky.pokeapi.accessor.PokeApiEggGroupResourceAccessorImpl
import io.lexadiky.pokeapi.accessor.PokeApiLanguageResourceAccessor
import io.lexadiky.pokeapi.accessor.PokeApiLanguageResourceAccessorImpl
import io.lexadiky.pokeapi.accessor.PokeApiMoveDamageClassResourceAccessor
import io.lexadiky.pokeapi.accessor.PokeApiMoveDamageClassResourceAccessorImpl
import io.lexadiky.pokeapi.accessor.PokeApiPokemonColorResourceAccessor
import io.lexadiky.pokeapi.accessor.PokeApiPokemonColorResourceAccessorImpl
import io.lexadiky.pokeapi.accessor.PokeApiPokemonResourceAccessor
import io.lexadiky.pokeapi.accessor.PokeApiPokemonResourceAccessorImpl
import io.lexadiky.pokeapi.accessor.PokeApiPokemonSpeciesResourceAccessor
import io.lexadiky.pokeapi.accessor.PokeApiPokemonSpeciesResourceAccessorImpl
import io.lexadiky.pokeapi.accessor.PokeApiStatResourceAccessor
import io.lexadiky.pokeapi.accessor.PokeApiStatResourceAccessorImpl
import io.lexadiky.pokeapi.accessor.PokeApiTypeResourceAccessor
import io.lexadiky.pokeapi.accessor.PokeApiTypeResourceAccessorImpl
import io.lexadiky.pokeapi.accessor.PokeApiVersionResourceAccessor
import io.lexadiky.pokeapi.accessor.PokeApiVersionResourceAccessorImpl
import io.lexadiky.pokeapi.impl.HttpRequester
import io.lexadiky.pokeapi.impl.NoOpPokeApiClientLogger
import io.lexadiky.pokeapi.util.CacheSettings
import io.lexadiky.pokeapi.util.PokeApiClientLogger

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
     * [egg-group](https://pokeapi.co/docs/v2#egg-group) resource
     */
    val eggGroup: PokeApiEggGroupResourceAccessor

    /**
     * [stats](https://pokeapi.co/docs/v2#stats) resource
     */
    val stat: PokeApiStatResourceAccessor

    /**
     * [move-damage-classes](https://pokeapi.co/docs/v2#move-damage-classes) resource
     */
    val moveDamageClass: PokeApiMoveDamageClassResourceAccessor

    /**
     * [characteristics](https://pokeapi.co/docs/v2#characteristics) resource
     */
    val characteristic: PokeApiCharacteristicResourceAccessor

    /**
     * Entry point for Fluid API
     */
    suspend fun <T> use(computation: suspend PokeApiFluidContext.() -> T): Result<T>
}

/**
 * Default [PokeApiClient] implementation using KTOR library
 */
internal class PokeApiClientImpl(private val builder: PokeApiClientBuilder) : PokeApiClient {

    private val requester: HttpRequester = HttpRequester(builder.logger, builder.host, builder.path, builder.cache)
    private val fluidContext: PokeApiFluidContext = PokeApiFluidContextImpl(requester, this)

    override val pokemon: PokeApiPokemonResourceAccessor = PokeApiPokemonResourceAccessorImpl(requester)
    override val type: PokeApiTypeResourceAccessor = PokeApiTypeResourceAccessorImpl(requester)
    override val ability: PokeApiAbilityResourceAccessor = PokeApiAbilityResourceAccessorImpl(requester)
    override val version: PokeApiVersionResourceAccessor = PokeApiVersionResourceAccessorImpl(requester)
    override val language: PokeApiLanguageResourceAccessor = PokeApiLanguageResourceAccessorImpl(requester)
    override val pokemonColor: PokeApiPokemonColorResourceAccessor = PokeApiPokemonColorResourceAccessorImpl(requester)
    override val pokemonSpecies: PokeApiPokemonSpeciesResourceAccessor = PokeApiPokemonSpeciesResourceAccessorImpl(requester)
    override val eggGroup: PokeApiEggGroupResourceAccessor = PokeApiEggGroupResourceAccessorImpl(requester)
    override val stat: PokeApiStatResourceAccessor = PokeApiStatResourceAccessorImpl(requester)
    override val moveDamageClass: PokeApiMoveDamageClassResourceAccessor = PokeApiMoveDamageClassResourceAccessorImpl(requester)
    override val characteristic: PokeApiCharacteristicResourceAccessor = PokeApiCharacteristicResourceAccessorImpl(requester)

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
    var cache: CacheSettings = CacheSettings.Disabled

    /**
     * [PokeApiClientLogger] implementation to log http requests made by library. No logging by default
     */
    var logger: PokeApiClientLogger = NoOpPokeApiClientLogger()

    /**
     * Builds [PokeApiClient]
     */
    internal fun build(): PokeApiClient = PokeApiClientImpl(this)
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
