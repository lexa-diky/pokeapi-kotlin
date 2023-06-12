package io.lexadiky.pokeapi

import io.lexadiky.pokeapi.accessor.GenericAccessor
import io.lexadiky.pokeapi.entity.ability.Ability
import io.lexadiky.pokeapi.entity.characteristic.Characteristic
import io.lexadiky.pokeapi.entity.egg.EggGroup
import io.lexadiky.pokeapi.entity.evolution.EvolutionChain
import io.lexadiky.pokeapi.entity.evolution.EvolutionTrigger
import io.lexadiky.pokeapi.entity.item.Item
import io.lexadiky.pokeapi.entity.language.Language
import io.lexadiky.pokeapi.entity.move.Move
import io.lexadiky.pokeapi.entity.move.MoveDamageClass
import io.lexadiky.pokeapi.entity.move.MoveTarget
import io.lexadiky.pokeapi.entity.pokemon.Pokemon
import io.lexadiky.pokeapi.entity.pokemon.PokemonColor
import io.lexadiky.pokeapi.entity.pokemon.PokemonSpecies
import io.lexadiky.pokeapi.entity.stat.Stat
import io.lexadiky.pokeapi.entity.type.Type
import io.lexadiky.pokeapi.entity.version.Version
import io.lexadiky.pokeapi.impl.HttpRequesterImpl
import io.lexadiky.pokeapi.impl.NoOpPokeApiClientLogger
import io.lexadiky.pokeapi.impl.PokeApiClientImpl
import io.lexadiky.pokeapi.network.CacheSettings
import io.lexadiky.pokeapi.network.HttpRequester
import io.lexadiky.pokeapi.util.PokeApiClientLogger
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/**
 * Entry point for PokeApi.
 *
 * Use either properties like [pokemon] to access normal REST style requests
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
     *[evolution-chain](https://pokeapi.co/docs/v2#evolution-chains) resource
     */
    val evolutionChain: GenericAccessor<EvolutionChain>

    /**
     *[evolution-triggers](https://pokeapi.co/docs/v2#evolution-triggers) resource
     */
    val evolutionTrigger: GenericAccessor<EvolutionTrigger>

    /**
     *[item](https://pokeapi.co/docs/v2#item) resource
     */
    val item: GenericAccessor<Item>
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
    internal fun build(): PokeApiClient = PokeApiClientImpl(
        HttpRequesterImpl(logger, host, path, timeout, cache)
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

/**
 * Allows to create [PokeApiClient] using fully custom [HttpRequester].
 */
fun PokeApiClient(requester: HttpRequester): PokeApiClient {
    return PokeApiClientImpl(requester)
}
