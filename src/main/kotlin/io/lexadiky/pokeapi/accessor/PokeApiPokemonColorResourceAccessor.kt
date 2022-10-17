package io.lexadiky.pokeapi.accessor

import io.lexadiky.pokeapi.entity.pokemon.PokemonColor
import io.lexadiky.pokeapi.impl.GenericAccessorImpl
import io.lexadiky.pokeapi.impl.HttpRequester

interface PokeApiPokemonColorResourceAccessor : GenericAccessor<PokemonColor>

internal class PokeApiPokemonColorResourceAccessorImpl(private val requester: HttpRequester) :
    PokeApiPokemonColorResourceAccessor, GenericAccessor<PokemonColor> by GenericAccessorImpl("pokemon-color", requester)
