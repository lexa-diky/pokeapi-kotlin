package io.lexadiky.pokeapi.entity.language

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Language(
    @SerialName("name") val name: String,
    @SerialName("id") val id: Int,
)
