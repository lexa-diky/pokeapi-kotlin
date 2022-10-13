package io.lexadiky.pokeapi.entity.pokemon

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonSpecies(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
)

