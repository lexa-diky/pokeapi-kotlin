package io.lexadiky.pokeapi.entity.common

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResouceList<Resource>(
    @SerialName("results") val results: List<ResourcePointer<Resource>>
): List<ResourcePointer<Resource>> by results