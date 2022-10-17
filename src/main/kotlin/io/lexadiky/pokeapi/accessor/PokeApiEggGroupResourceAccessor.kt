package io.lexadiky.pokeapi.accessor

import io.lexadiky.pokeapi.entity.egg.EggGroup
import io.lexadiky.pokeapi.entity.version.Version
import io.lexadiky.pokeapi.impl.GenericAccessorImpl
import io.lexadiky.pokeapi.impl.HttpRequester

interface PokeApiEggGroupResourceAccessor : GenericAccessor<EggGroup>

internal class PokeApiEggGroupResourceAccessorImpl(private val requester: HttpRequester) :
    PokeApiEggGroupResourceAccessor, GenericAccessor<EggGroup> by GenericAccessorImpl("egg-group", requester)
