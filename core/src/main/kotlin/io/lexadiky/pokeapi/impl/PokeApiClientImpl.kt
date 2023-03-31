package io.lexadiky.pokeapi.impl

import io.lexadiky.pokeapi.PokeApiClient
import io.lexadiky.pokeapi.PokeApiFluidContext
import io.lexadiky.pokeapi.PokeApiFluidContextImpl
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
import io.lexadiky.pokeapi.entity.version.Version
import io.lexadiky.pokeapi.network.HttpRequester

/**
 * Default [PokeApiClient] implementation using KTOR library
 */
internal class PokeApiClientImpl(requester: HttpRequester) : PokeApiClient {
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
    override val characteristic: GenericAccessor<Characteristic> = GenericAccessorImpl("characteristic", requester)
    override val moveTarget: GenericAccessor<MoveTarget> = GenericAccessorImpl("move-target", requester)
    override val move: GenericAccessor<Move> = GenericAccessorImpl("move", requester)

    override suspend fun <T> use(computation: suspend PokeApiFluidContext.() -> T): Result<T> {
        return runCatching { fluidContext.computation() }
    }
}
