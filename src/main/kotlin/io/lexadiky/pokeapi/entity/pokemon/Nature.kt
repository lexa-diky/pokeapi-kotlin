package io.lexadiky.pokeapi.entity.pokemon

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Nature(
    @SerialName("name") val name: String,
    @SerialName("id") val id: Int,
)
