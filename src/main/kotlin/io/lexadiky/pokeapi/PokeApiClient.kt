package io.lexadiky.pokeapi

import io.ktor.http.content.Version
import io.lexadiky.pokeapi.accessor.GenericAccessor
import io.lexadiky.pokeapi.entity.ability.Ability
import io.lexadiky.pokeapi.entity.characteristic.Characteristic
import io.lexadiky.pokeapi.entity.egg.EggGroup
import io.lexadiky.pokeapi.entity.language.Language
import io.lexadiky.pokeapi.entity.move.Move
import io.lexadiky.pokeapi.entity.move.MoveDamageClass
import io.lexadiky.pokeapi.entity.move.MoveTarget
import io.lexadiky.pokeapi.entity.pokemon.Pokemon
import io.lexadiky.pokeapi.entity.pokemon.PokemonColor
import io.lexadiky.pokeapi.entity.pokemon.PokemonSpecies
import io.lexadiky.pokeapi.entity.stat.Stat
import io.lexadiky.pokeapi.entity.type.Type
import io.lexadiky.pokeapi.impl.GenericAccessorImpl
import io.lexadiky.pokeapi.impl.HttpRequesterImpl
import io.lexadiky.pokeapi.impl.NoOpPokeApiClientLogger
import io.lexadiky.pokeapi.network.CacheSettings
import io.lexadiky.pokeapi.network.HttpRequester
import io.lexadiky.pokeapi.util.PokeApiClientLogger
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/**
 * Entry point for PokeApi.
 *
 * Use either properties like [pokemon] to access normal REST style requests or [use] for Fluid API
 */
interface PokeApiClient {

    /**
     * [pokemon](https://pokeapi.co/docs/v2#pokemon) resource
     */
    val pokemon: GenericAccessor<Pokemon>

    /**
     * [type](https://pokeapi.co/docs/v2#type) resource
     */
    val type: GenericAccessor<Type>

    /**
     * [ability](https://pokeapi.co/docs/v2#ability) resource
     */
    val ability: GenericAccessor<Ability>

    /**
     * [version](https://pokeapi.co/docs/v2#version) resource
     */
    val version: GenericAccessor<Version>

    /**
     * [languages](https://pokeapi.co/docs/v2#languages) resource
     */
    val language: GenericAccessor<Language>

    /**
     * [pokemon-colors](https://pokeapi.co/docs/v2#pokemon-colors) resource
     */
    val pokemonColor: GenericAccessor<PokemonColor>

    /**
     * [pokemon-species](https://pokeapi.co/docs/v2#pokemon-species) resource
     */
    val pokemonSpecies: GenericAccessor<PokemonSpecies>

    /**
     * [egg-group](https://pokeapi.co/docs/v2#egg-group) resource
     */
    val eggGroup: GenericAccessor<EggGroup>

    /**
     * [stats](https://pokeapi.co/docs/v2#stats) resource
     */
    val stat: GenericAccessor<Stat>

    /**
     * [move-damage-classes](https://pokeapi.co/docs/v2#move-damage-classes) resource
     */
    val moveDamageClass: GenericAccessor<MoveDamageClass>

    /**
     * [characteristics](https://pokeapi.co/docs/v2#characteristics) resource
     */
    val characteristic: GenericAccessor<Characteristic>

    /**
     * [move-target](https://pokeapi.co/docs/v2#move-target) resource
     */
    val moveTarget: GenericAccessor<MoveTarget>

    /**
     *[move](https://pokeapi.co/docs/v2#move) resource
     */
    val move: GenericAccessor<Move>

    /**
     * Entry point for Fluid API
     */
    suspend fun <T> use(computation: suspend PokeApiFluidContext.() -> T): Result<T>
}

/**
 * Default [PokeApiClient] implementation using KTOR library
 */
internal class PokeApiClientImpl(builder: PokeApiClientBuilder) : PokeApiClient {

    private val requester: HttpRequester = HttpRequesterImpl(builder.logger, builder.host, builder.path, builder.cache, builder.timeout)
    private val fluidContext: PokeApiFluidContext = PokeApiFluidContextImpl(requester, this)

    override val pokemon: GenericAccessor<Pokemon> = GenericAccessorImpl("pokemon", requester)
    override val type: GenericAccessor<Type> = GenericAccessorImpl("type", requester)
    override val ability: GenericAccessor<Ability> = GenericAccessorImpl("ability", requester)
    override val version: GenericAccessor<Version> = GenericAccessorImpl("version", requester)
    override val language: GenericAccessor<Language> = GenericAccessorImpl("language", requester)
    override val pokemonColor: GenericAccessor<PokemonColor> = GenericAccessorImpl("pokemon-color", requester)
    override val pokemonSpecies: GenericAccessor<PokemonSpecies> = GenericAccessorImpl("pokemon-species", requester)
    override val eggGroup: GenericAccessor<EggGroup> = GenericAccessorImpl("egg-group", requester)
    override val stat: GenericAccessor<Stat> = GenericAccessorImpl("stat", requester)
    override val moveDamageClass: GenericAccessor<MoveDamageClass> = GenericAccessorImpl("move-damage-class", requester)
    override val characteristic: GenericAccessor<Characteristic> =GenericAccessorImpl("characteristic", requester)
    override val moveTarget: GenericAccessor<MoveTarget> = GenericAccessorImpl("move-target", requester)
    override val move: GenericAccessor<Move> = GenericAccessorImpl("move", requester)

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
     * Request timeout value
     */
    var timeout: Duration = 10.seconds

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
