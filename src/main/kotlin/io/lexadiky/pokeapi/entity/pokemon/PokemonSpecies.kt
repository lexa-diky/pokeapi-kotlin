package io.lexadiky.pokeapi.entity.pokemon

import io.lexadiky.pokeapi.entity.common.HasResourcePointer
import io.lexadiky.pokeapi.entity.common.Name
import io.lexadiky.pokeapi.entity.common.ResourcePointer
import io.lexadiky.pokeapi.entity.egg.EggGroup
import io.lexadiky.pokeapi.entity.evolution.EvolutionChain
import io.lexadiky.pokeapi.entity.generation.Generation
import io.lexadiky.pokeapi.entity.language.Language
import io.lexadiky.pokeapi.entity.version.Pokedex
import io.lexadiky.pokeapi.entity.version.Version
import io.lexadiky.pokeapi.entity.zone.PalParkArea
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonSpecies(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("base_happiness") val baseHappiness: Int?,
    @SerialName("capture_rate") val captureRate: Int,
    @SerialName("color") val color: ResourcePointer<PokemonColor>,
    @SerialName("egg_groups") val eggGroups: List<ResourcePointer<EggGroup>>,
    @SerialName("evolution_chain") val evolutionChain: ResourcePointer<EvolutionChain>,
    @SerialName("evolves_from_species") val evolvesFromSpecies: ResourcePointer<PokemonSpecies>?,
    @SerialName("flavor_text_entries") val flavorTextEntries: List<FlavorTextEntry>,
    @SerialName("form_descriptions") val formDescriptions: List<FormDescription>,
    @SerialName("forms_switchable") val formsSwitchable: Boolean,
    @SerialName("gender_rate") val genderRate: Int,
    @SerialName("genera") val genera: List<Genera>,
    @SerialName("generation") val generation: ResourcePointer<Generation>,
    @SerialName("growth_rate") val growthRate: ResourcePointer<GrowthRate>,
    @SerialName("habitat") val habitat: ResourcePointer<PokemonHabitat>?,
    @SerialName("has_gender_differences") val hasGenderDifferences: Boolean,
    @SerialName("hatch_counter") val hatchCounter: Int?,
    @SerialName("is_baby") val isBaby: Boolean,
    @SerialName("is_legendary") val isLegendary: Boolean,
    @SerialName("is_mythical") val isMythical: Boolean,
    @SerialName("names") val names: List<Name>,
    @SerialName("order") val order: Int,
    @SerialName("pal_park_encounters") val palParkEncounters: List<PalParkEncounter>,
    @SerialName("pokedex_numbers") val pokedexNumbers: List<PokedexNumber>,
    @SerialName("shape") val shape: ResourcePointer<Shape>?,
    @SerialName("varieties") val varieties: List<VarietySlot>
) {

    @Serializable
    data class FlavorTextEntry(
        @SerialName("flavor_text") val flavorText: String,
        @SerialName("language") val language: ResourcePointer<Language>,
        @SerialName("version") val version: ResourcePointer<Version>,
    )

    @Serializable
    data class FormDescription(
        @SerialName("description") val description: String,
        @SerialName("language") val language: ResourcePointer<Language>,
    )

    @Serializable
    data class Genera(
        @SerialName("genus") val genus: String,
        @SerialName("language") val language: ResourcePointer<Language>,
    )

    @Serializable
    data class PalParkEncounter(
        @SerialName("area") val area: ResourcePointer<PalParkArea>,
        @SerialName("base_score") val baseScore: Int,
        @SerialName("rate") val rate: Int
    ) : HasResourcePointer<PalParkArea> {

        override val pointer: ResourcePointer<PalParkArea> = area
    }

    @Serializable
    data class PokedexNumber(
        @SerialName("entry_number") val entryNumber: Int,
        @SerialName("pokedex") val pokedex: ResourcePointer<Pokedex>
    )

    @Serializable
    data class VarietySlot(
        @SerialName("is_default") val isDefault: Boolean,
        @SerialName("pokemon") val pokemon: ResourcePointer<Pokemon>
    ): HasResourcePointer<Pokemon> {

        override val pointer: ResourcePointer<Pokemon> = pokemon
    }
}

