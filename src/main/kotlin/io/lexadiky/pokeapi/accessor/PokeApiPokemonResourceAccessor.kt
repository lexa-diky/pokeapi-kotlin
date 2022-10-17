package io.lexadiky.pokeapi.accessor

import io.lexadiky.pokeapi.impl.HttpRequester
import io.lexadiky.pokeapi.entity.pokemon.Pokemon
import io.lexadiky.pokeapi.impl.GenericAccessorImpl

interface PokeApiPokemonResourceAccessor : GenericAccessor<Pokemon>

internal class PokeApiPokemonResourceAccessorImpl(private val requester: HttpRequester) :
    PokeApiPokemonResourceAccessor, GenericAccessor<Pokemon> by GenericAccessorImpl("pokemon", requester)
