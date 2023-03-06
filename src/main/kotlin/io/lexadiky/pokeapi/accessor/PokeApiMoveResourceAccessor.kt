package io.lexadiky.pokeapi.accessor

import io.lexadiky.pokeapi.entity.move.Move
import io.lexadiky.pokeapi.entity.pokemon.PokemonColor
import io.lexadiky.pokeapi.impl.GenericAccessorImpl
import io.lexadiky.pokeapi.impl.HttpRequester

interface PokeApiMoveResourceAccessor : GenericAccessor<Move>

internal class PokeApiMoveResourceAccessorImpl(private val requester: HttpRequester) :
    PokeApiMoveResourceAccessor, GenericAccessor<Move> by GenericAccessorImpl("move", requester)
