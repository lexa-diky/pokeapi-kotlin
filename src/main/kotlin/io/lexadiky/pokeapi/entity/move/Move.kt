package io.lexadiky.pokeapi.entity.move

import io.lexadiky.pokeapi.entity.ability.Ability
import io.lexadiky.pokeapi.entity.common.Name
import io.lexadiky.pokeapi.entity.common.ResourcePointer
import io.lexadiky.pokeapi.entity.language.Language
import io.lexadiky.pokeapi.entity.pokemon.Pokemon
import io.lexadiky.pokeapi.entity.type.Type
import io.lexadiky.pokeapi.entity.version.VersionGroup
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable // TODO add rest of the fields
data class Move(
    @SerialName("name") val name: String,
    @SerialName("id") val id: Int,
    @SerialName("target") val target: ResourcePointer<MoveTarget>,
    @SerialName("type") val type: ResourcePointer<Type>,
    @SerialName("accuracy") val accuracy: Int?,
    @SerialName("damage_class") val damageClass: ResourcePointer<MoveDamageClass>,
    @SerialName("names") val names: List<Name>,
    @SerialName("power") val power: Int?,
    @SerialName("pp") val pp: Int?,
    @SerialName("priority") val priority: Int,
    @SerialName("effect_chance") val effectChance: Int?,
    @SerialName("flavor_text_entries") val flavorTextEntries: List<FlavorTextEntry>,
    @SerialName("effect_entries") val effectEntries: List<Ability.EffectEntry>,
    @SerialName("learned_by_pokemon") val learnedByPokemon: List<ResourcePointer<Pokemon>>
) {

    @Serializable
    data class FlavorTextEntry(
        @SerialName("flavor_text") val flavorText: String,
        @SerialName("language") val language: ResourcePointer<Language>,
        @SerialName("version_group") val versionGroup: ResourcePointer<VersionGroup>,
    )

    @Serializable
    data class EffectEntry(
        @SerialName("effect") val effect: String,
        @SerialName("short_effect") val shortEffect: String,
        @SerialName("language") val language: ResourcePointer<Language>,
    )
}
