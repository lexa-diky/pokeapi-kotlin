package io.lexadiky.pokeapi.entity.move

import io.lexadiky.pokeapi.entity.common.Name
import io.lexadiky.pokeapi.entity.common.ResourcePointer
import io.lexadiky.pokeapi.entity.type.Type
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable // TODO add rest of the fields
data class Move(
    @SerialName("name") val name: String,
    @SerialName("id") val id: Int,
    @SerialName("target") val target: ResourcePointer<MoveTarget>,
    @SerialName("type") val type: ResourcePointer<Type>,
    @SerialName("accuracy") val accuracy: Int,
    @SerialName("damage_class") val damageClass: ResourcePointer<MoveDamageClass>,
    @SerialName("names") val names: List<Name>,
    @SerialName("power") val power: Int,
    @SerialName("pp") val pp: Int,
    @SerialName("priority") val priority: Int
)
