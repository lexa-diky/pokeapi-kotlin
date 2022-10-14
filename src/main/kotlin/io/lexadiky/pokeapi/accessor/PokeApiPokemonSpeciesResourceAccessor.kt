package io.lexadiky.pokeapi.accessor

import io.lexadiky.pokeapi.impl.HttpRequester
import io.lexadiky.pokeapi.entity.ability.Ability
import io.lexadiky.pokeapi.entity.pokemon.PokemonSpecies

interface PokeApiPokemonSpeciesResourceAccessor : GenericAccessor<PokemonSpecies>

internal class PokeApiPokemonSpeciesResourceAccessorImpl(private val requester: HttpRequester) :
    PokeApiPokemonSpeciesResourceAccessor, GenericAccessor<PokemonSpecies> by GenericAccessorImpl("pokemon-species", requester)
