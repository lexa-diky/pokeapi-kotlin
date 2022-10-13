package io.lexadiky.pokeapi.entity.pokemon

import io.lexadiky.pokeapi.entity.ability.Ability
import io.lexadiky.pokeapi.entity.common.HasResourcePinter
import io.lexadiky.pokeapi.entity.common.ResourcePointer
import io.lexadiky.pokeapi.entity.item.Item
import io.lexadiky.pokeapi.entity.move.Move
import io.lexadiky.pokeapi.entity.move.MoveLearnMethod
import io.lexadiky.pokeapi.entity.type.Type
import io.lexadiky.pokeapi.entity.version.Version
import io.lexadiky.pokeapi.entity.version.VersionGroup
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonSpecies(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
)

