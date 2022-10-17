package io.lexadiky.pokeapi.accessor

import io.lexadiky.pokeapi.entity.move.MoveDamageClass
import io.lexadiky.pokeapi.impl.HttpRequester

interface PokeApiMoveDamageClassResourceAccessor : GenericAccessor<MoveDamageClass>

internal class PokeApiMoveDamageClassResourceAccessorImpl(private val requester: HttpRequester) :
    PokeApiMoveDamageClassResourceAccessor, GenericAccessor<MoveDamageClass> by GenericAccessorImpl("move-damage-class", requester)
