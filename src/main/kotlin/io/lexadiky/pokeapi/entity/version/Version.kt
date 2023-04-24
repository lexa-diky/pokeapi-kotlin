package io.lexadiky.pokeapi.entity.version

import io.lexadiky.pokeapi.entity.common.Name
import io.lexadiky.pokeapi.entity.common.ResourcePointer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Version(
    @SerialName("name") val name: String,
    @SerialName("id") val id: Int,
    @SerialName("names") val names: List<Name>,
    @SerialName("version_group") val versionGroup: ResourcePointer<VersionGroup>
)
