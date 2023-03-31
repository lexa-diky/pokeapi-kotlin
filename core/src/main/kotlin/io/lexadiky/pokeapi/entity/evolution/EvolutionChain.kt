package io.lexadiky.pokeapi.entity.evolution

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EvolutionChain(
    @SerialName("name") val name: String,
    @SerialName("id") val id: Int,
)
