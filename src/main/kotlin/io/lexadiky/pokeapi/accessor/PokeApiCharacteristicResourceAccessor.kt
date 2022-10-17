package io.lexadiky.pokeapi.accessor

import io.lexadiky.pokeapi.impl.HttpRequester
import io.lexadiky.pokeapi.entity.characteristic.Characteristic
import io.lexadiky.pokeapi.impl.GenericAccessorImpl

interface PokeApiCharacteristicResourceAccessor : GenericAccessor<Characteristic>

internal class PokeApiCharacteristicResourceAccessorImpl(private val requester: HttpRequester) :
    PokeApiCharacteristicResourceAccessor, GenericAccessor<Characteristic> by GenericAccessorImpl("characteristic", requester)
