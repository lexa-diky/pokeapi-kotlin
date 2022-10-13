package io.lexadiky.pokeapi.accessor

import io.lexadiky.pokeapi.impl.HttpRequester
import io.lexadiky.pokeapi.entity.ability.Ability

interface PokeApiAbilityResourceAccessor : GenericAccessor<Ability>

internal class PokeApiAbilityResourceAccessorImpl(private val requester: HttpRequester) :
    PokeApiAbilityResourceAccessor, GenericAccessor<Ability> by GenericAccessorImpl("ability", requester)