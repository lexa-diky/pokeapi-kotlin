package io.lexadiky.pokeapi.accessor

import io.lexadiky.pokeapi.entity.move.MoveTarget
import io.lexadiky.pokeapi.impl.GenericAccessorImpl
import io.lexadiky.pokeapi.impl.HttpRequester

interface MoveTargetResourceAccessor : GenericAccessor<MoveTarget>

internal class MoveTargetResourceAccessorImpl(private val requester: HttpRequester) :
    MoveTargetResourceAccessor, GenericAccessor<MoveTarget> by GenericAccessorImpl("move-target", requester)
