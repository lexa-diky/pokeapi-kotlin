package io.lexadiky.pokeapi.entity.version

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Version(
    @SerialName("name") val name: String,
    @SerialName("id") val id: Int,
)
