package io.lexadiky.pokeapi.entity.stat

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Stat(
    @SerialName("name") val name: String,
    @SerialName("id") val id: Int,
)
