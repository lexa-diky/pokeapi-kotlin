package io.lexadiky.pokeapi.entity.item

import io.lexadiky.pokeapi.entity.common.Name
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Item(
    @SerialName("name") val name: String,
    @SerialName("id") val id: Int,
    @SerialName("names") val names: List<Name>
)
