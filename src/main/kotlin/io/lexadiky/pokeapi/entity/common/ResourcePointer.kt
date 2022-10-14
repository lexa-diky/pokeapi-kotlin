package io.lexadiky.pokeapi.entity.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Pointer to some resource, could be used to fetch it keeping type safety
 */
@Serializable
data class ResourcePointer<Resource>(
    @SerialName("name") val name: String? = null,
    @SerialName("url") val url: String,
)
