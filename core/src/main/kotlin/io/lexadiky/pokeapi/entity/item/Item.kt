package io.lexadiky.pokeapi.entity.item

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Item(
    @SerialName("name") val name: String,
    @SerialName("id") val id: Int,
)
