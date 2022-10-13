package io.lexadiky.pokeapi.entity.generation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Generation(
    @SerialName("name") val name: String,
    @SerialName("id") val id: Int,

)
