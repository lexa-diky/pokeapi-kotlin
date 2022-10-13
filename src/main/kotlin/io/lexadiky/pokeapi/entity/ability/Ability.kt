package io.lexadiky.pokeapi.entity.ability

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Ability(
    @SerialName("name") val name: String,
    @SerialName("id") val id: Int,
)