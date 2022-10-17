package io.lexadiky.pokeapi.entity.characteristic

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Characteristic(
    @SerialName("name") val name: String,
    @SerialName("id") val id: Int,
)
