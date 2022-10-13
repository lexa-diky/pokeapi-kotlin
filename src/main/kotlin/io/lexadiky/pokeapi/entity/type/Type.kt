package io.lexadiky.pokeapi.entity.type

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Type(
    @SerialName("name") val name: String,
    @SerialName("id") val id: Int,
)
