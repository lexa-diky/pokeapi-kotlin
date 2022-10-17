package io.lexadiky.pokeapi.accessor

import io.lexadiky.pokeapi.entity.version.Version
import io.lexadiky.pokeapi.impl.GenericAccessorImpl
import io.lexadiky.pokeapi.impl.HttpRequester

interface PokeApiVersionResourceAccessor : GenericAccessor<Version>

internal class PokeApiVersionResourceAccessorImpl(private val requester: HttpRequester) :
    PokeApiVersionResourceAccessor, GenericAccessor<Version> by GenericAccessorImpl("version", requester)
