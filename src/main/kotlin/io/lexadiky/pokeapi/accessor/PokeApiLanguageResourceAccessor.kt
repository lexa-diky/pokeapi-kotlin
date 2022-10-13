package io.lexadiky.pokeapi.accessor

import io.lexadiky.pokeapi.entity.language.Language
import io.lexadiky.pokeapi.impl.HttpRequester

interface PokeApiLanguageResourceAccessor : GenericAccessor<Language>

internal class PokeApiLanguageResourceAccessorImpl(private val requester: HttpRequester) :
    PokeApiLanguageResourceAccessor, GenericAccessor<Language> by GenericAccessorImpl("language", requester)
