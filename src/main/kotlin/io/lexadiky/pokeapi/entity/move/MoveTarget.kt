package io.lexadiky.pokeapi.entity.move

import io.lexadiky.pokeapi.entity.common.Name
import io.lexadiky.pokeapi.entity.common.ResourcePointer
import io.lexadiky.pokeapi.entity.language.Language
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoveTarget(
    @SerialName("descriptions")
    val descriptions: List<Description>,
    @SerialName("id")
    val id: Int,
    @SerialName("moves")
    val moves: List<ResourcePointer<Move>>,
    @SerialName("name")
    val name: String,
    @SerialName("names")
    val names: List<Name>
) {

    @Serializable
    data class Description(
        @SerialName("description")
        val description: String,
        @SerialName("language")
        val language: ResourcePointer<Language>
    )
}
