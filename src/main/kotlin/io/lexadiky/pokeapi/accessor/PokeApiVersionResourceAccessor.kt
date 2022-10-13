package io.lexadiky.pokeapi.accessor

import io.lexadiky.pokeapi.impl.HttpRequester
import io.lexadiky.pokeapi.entity.ability.Ability
import io.lexadiky.pokeapi.entity.version.Version

interface PokeApiVersionResourceAccessor : GenericAccessor<Version>

internal class PokeApiVersionResourceAccessorImpl(private val requester: HttpRequester) :
    PokeApiVersionResourceAccessor, GenericAccessor<Version> by GenericAccessorImpl("version", requester)