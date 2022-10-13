package io.lexadiky.pokeapi.entity.language

import io.lexadiky.pokeapi.entity.common.Name
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Language(
    @SerialName("name") val name: String,
    @SerialName("id") val id: Int,
    @SerialName("iso3166") val iso3166: String,
    @SerialName("iso639") val iso639: String,
    @SerialName("official") val official: Boolean,
    @SerialName("names") val names: List<Name>
)
