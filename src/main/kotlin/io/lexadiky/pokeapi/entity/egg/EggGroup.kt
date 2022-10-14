package io.lexadiky.pokeapi.entity.egg

import io.lexadiky.pokeapi.entity.common.Name
import io.lexadiky.pokeapi.entity.common.ResourcePointer
import io.lexadiky.pokeapi.entity.pokemon.PokemonSpecies
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EggGroup(
    @SerialName("name") val name: String,
    @SerialName("id") val id: Int,
    @SerialName("names") val names: List<Name>,
    @SerialName("pokemon_species") val pokemonSpecies: List<ResourcePointer<PokemonSpecies>>,
)
