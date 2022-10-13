package io.lexadiky.pokeapi.entity.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResourcePointer<Resource>(
    @SerialName("name") val name: String,
    @SerialName("url") val url: String,
)
