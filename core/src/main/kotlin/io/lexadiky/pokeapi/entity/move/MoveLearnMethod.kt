package io.lexadiky.pokeapi.entity.move

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoveLearnMethod(
    @SerialName("name") val name: String,
    @SerialName("id") val id: Int,
)
