package io.lexadiky.pokeapi.entity.move

import io.lexadiky.pokeapi.entity.common.Name
import io.lexadiky.pokeapi.entity.common.ResourcePointer
import io.lexadiky.pokeapi.entity.language.Language
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoveDamageClass(
    @SerialName("name") val name: String,
    @SerialName("id") val id: Int,
    @SerialName("descriptions") val descriptions: List<Description>,
    @SerialName("moves") val moves: List<ResourcePointer<Move>>,
    @SerialName("names") val names: List<Name>
) {

    @Serializable
    data class Description(
        val description: String,
        val language: ResourcePointer<Language>
    )
}
