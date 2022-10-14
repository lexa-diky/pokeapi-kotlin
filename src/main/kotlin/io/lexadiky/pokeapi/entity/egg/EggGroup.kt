package io.lexadiky.pokeapi.entity.egg

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EggGroup(
    @SerialName("name") val name: String,
    @SerialName("id") val id: Int,
)
