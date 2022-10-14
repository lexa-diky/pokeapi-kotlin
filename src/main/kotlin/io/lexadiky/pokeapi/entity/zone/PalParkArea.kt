package io.lexadiky.pokeapi.entity.zone

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PalParkArea(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
)