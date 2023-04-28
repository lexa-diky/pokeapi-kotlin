package io.lexadiky.pokeapi.entity.evolution

import io.lexadiky.pokeapi.entity.common.ResourcePointer
import io.lexadiky.pokeapi.entity.item.Item
import io.lexadiky.pokeapi.entity.locations.Location
import io.lexadiky.pokeapi.entity.move.Move
import io.lexadiky.pokeapi.entity.pokemon.PokemonSpecies
import io.lexadiky.pokeapi.entity.type.Type
import io.lexadiky.pokeapi.entity.common.RelativePhysicalStats
import io.lexadiky.pokeapi.entity.common.TimeOfDay
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EvolutionChain(
    @SerialName("id") val id: Int,
    @SerialName("baby_trigger_item") val babyTriggerItem: ResourcePointer<Item>?,
    @SerialName("chain") val chain: ChainLink
) {

    @Serializable
    data class ChainLink(
        @SerialName("is_baby") val isBaby: Boolean,
        @SerialName("species") val species: ResourcePointer<PokemonSpecies>,
        @SerialName("evolution_details") val evolutionDetails: List<EvolutionDetails>,
        @SerialName("evolves_to") val evolvesTo: List<ChainLink>
    )

    @Serializable
    data class EvolutionDetails(
        @SerialName("item") val item: ResourcePointer<Item>?,
        @SerialName("trigger") val trigger: ResourcePointer<EvolutionTrigger>?,
        @SerialName("gender") val gender: Int?,
        @SerialName("held_item") val heldtem: ResourcePointer<Item>?,
        @SerialName("known_move") val knownMove: ResourcePointer<Move>?,
        @SerialName("known_move_type") val knownMoveType: ResourcePointer<Type>?,
        @SerialName("location") val location: ResourcePointer<Location>?,
        @SerialName("min_level") val minLevel: Int?,
        @SerialName("min_happiness") val minHappiness: Int?,
        @SerialName("min_beauty") val minBeauty: Int?,
        @SerialName("min_affection") val minAffection: Int?,
        @SerialName("needs_overworld_rain") val needsOverworldRain: Boolean,
        @SerialName("party_species") val partySpecies: ResourcePointer<PokemonSpecies>?,
        @SerialName("party_type") val partyType: ResourcePointer<Type>?,
        @SerialName("relative_physical_stats") private val relativePhysicalStatsInt: Int?,
        @SerialName("time_of_day") val timeOfDay: TimeOfDay,
        @SerialName("trade_species") val tradeSpecies: ResourcePointer<PokemonSpecies>?,
        @SerialName("turn_upside_down") val turnUpsideDown: Boolean
    ) {

        val relativePhysicalStats: RelativePhysicalStats? = when {
            relativePhysicalStatsInt == null -> null
            relativePhysicalStatsInt > 0 -> RelativePhysicalStats.MoreAttack
            relativePhysicalStatsInt < 0 -> RelativePhysicalStats.MoreDefence
            relativePhysicalStatsInt == 0 -> RelativePhysicalStats.Equal
            else -> null
        }
    }
}
