package io.lexadiky.pokeapi.entity.common

import io.lexadiky.pokeapi.entity.language.Language
import kotlinx.serialization.Serializable

@Serializable
data class Name(
    val name: String,
    val language: ResourcePointer<Language>
)