package io.lexadiky.pokeapi.accessor

import io.lexadiky.pokeapi.entity.stat.Stat
import io.lexadiky.pokeapi.impl.HttpRequester

interface PokeApiStatResourceAccessor : GenericAccessor<Stat>

internal class PokeApiStatResourceAccessorImpl(private val requester: HttpRequester) :
    PokeApiStatResourceAccessor, GenericAccessor<Stat> by GenericAccessorImpl("stat", requester)
