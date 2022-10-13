package io.lexadiky.pokeapi.entity.ability

import io.lexadiky.pokeapi.entity.common.HasResourcePinter
import io.lexadiky.pokeapi.entity.common.Name
import io.lexadiky.pokeapi.entity.common.ResourcePointer
import io.lexadiky.pokeapi.entity.generation.Generation
import io.lexadiky.pokeapi.entity.language.Language
import io.lexadiky.pokeapi.entity.pokemon.Pokemon
import io.lexadiky.pokeapi.entity.version.VersionGroup
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Ability(
    @SerialName("name") val name: String,
    @SerialName("id") val id: Int,
    @SerialName("generation") val generation: ResourcePointer<Generation>,
    @SerialName("is_main_series") val isMainSeries: Boolean,
    @SerialName("names") val names: List<Name>,
    @SerialName("pokemon") val pokemon: List<PokemonSlot>,
    @SerialName("flavor_text_entries") val flavorTextEntries: List<FlavorTextEntry>,
    @SerialName("effect_entries") val effectEntries: List<EffectEntry>,
    @SerialName("effect_changes") val effectChanges: List<EffectChange>
) {

    @Serializable
    data class PokemonSlot(
        @SerialName("pokemon") val pokemon: ResourcePointer<Pokemon>,
        @SerialName("slot") val slot: Int,
        @SerialName("is_hidden") val isHidden: Boolean,
    ) : HasResourcePinter<Pokemon> {

        override val pointer: ResourcePointer<Pokemon> = pokemon
    }

    @Serializable
    data class FlavorTextEntry(
        @SerialName("flavor_text") val flavorText: String,
        @SerialName("language") val language: ResourcePointer<Language>,
        @SerialName("version_group") val versionGroup: ResourcePointer<VersionGroup>,
    )

    @Serializable
    data class EffectEntry(
        @SerialName("effect") val flavorText: String,
        @SerialName("short_effect") val versionGroup: String,
        @SerialName("language") val language: ResourcePointer<Language>,
    )

    @Serializable
    data class EffectChange(
        @SerialName("version_group") val versionGroup: ResourcePointer<VersionGroup>,
        @SerialName("effect_entries") val effectEntries: List<Entry>
    ) {

        @Serializable
        data class Entry(
            @SerialName("effect") val flavorText: String,
            @SerialName("language") val language: ResourcePointer<Language>,
        )
    }
}