package io.lexadiky.pokeapi.entity.pokemon

import io.lexadiky.pokeapi.entity.common.Name
import io.lexadiky.pokeapi.entity.common.ResourcePointer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonColor(
    @SerialName("name") val name: String,
    @SerialName("id") val id: Int,
    @SerialName("names") val names: List<Name>,
    @SerialName("pokemon_species") val pokemonSpecies: List<ResourcePointer<PokemonSpecies>>
)
