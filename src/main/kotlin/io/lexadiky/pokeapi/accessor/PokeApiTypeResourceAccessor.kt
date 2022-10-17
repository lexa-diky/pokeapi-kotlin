package io.lexadiky.pokeapi.accessor

import io.lexadiky.pokeapi.impl.HttpRequester
import io.lexadiky.pokeapi.entity.type.Type
import io.lexadiky.pokeapi.impl.GenericAccessorImpl

interface PokeApiTypeResourceAccessor : GenericAccessor<Type>

internal class PokeApiTypeResourceAccessorImpl(private val requester: HttpRequester) :
    PokeApiTypeResourceAccessor, GenericAccessor<Type> by GenericAccessorImpl("type", requester)
