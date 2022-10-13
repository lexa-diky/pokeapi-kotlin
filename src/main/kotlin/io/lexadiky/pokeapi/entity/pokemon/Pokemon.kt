package io.lexadiky.pokeapi.entity.pokemon

import io.lexadiky.pokeapi.entity.ability.Ability
import io.lexadiky.pokeapi.entity.common.HasResourcePinter
import io.lexadiky.pokeapi.entity.common.ResourcePointer
import io.lexadiky.pokeapi.entity.generation.Generation
import io.lexadiky.pokeapi.entity.item.Item
import io.lexadiky.pokeapi.entity.move.Move
import io.lexadiky.pokeapi.entity.move.MoveLearnMethod
import io.lexadiky.pokeapi.entity.pokemon.sprites.Sprites
import io.lexadiky.pokeapi.entity.stat.Stat
import io.lexadiky.pokeapi.entity.type.Type
import io.lexadiky.pokeapi.entity.version.Version
import io.lexadiky.pokeapi.entity.version.VersionGroup
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Pokemon(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("types") val types: List<TypeSlot>,
    @SerialName("past_types") val pastTypes: List<PastTypeSlot>,
    @SerialName("base_experience") val baseExperience: Int,
    @SerialName("height") val height: Int,
    @SerialName("is_default") val isDefault: Boolean,
    @SerialName("order") val order: Int,
    @SerialName("weight") val weight: Int,
    @SerialName("abilities") val abilities: List<AbilitySlot>,
    @SerialName("forms") val forms: List<ResourcePointer<PokemonForm>>,
    @SerialName("game_indices") val gameIndices: List<GameIndexSlot>,
    @SerialName("held_items") val heldItems: List<HeldItemSlot>,
    @SerialName("moves") val moves: List<MoveSlot>,
    @SerialName("species") val species: ResourcePointer<PokemonSpecies>,
    @SerialName("sprites") val sprites: Sprites,
    @SerialName("stats") val stats: List<StatSlot>
) {

    @Serializable
    data class TypeSlot(
        @SerialName("slot") val slot: Int,
        @SerialName("type") val type: ResourcePointer<Type>,
    ) : HasResourcePinter<Type> {

        override val pointer: ResourcePointer<Type> = type
    }

    @Serializable
    data class PastTypeSlot(
        @SerialName("generation") val generation: ResourcePointer<Generation>,
        @SerialName("types") val types: List<TypeSlot>,
    )

    @Serializable
    data class AbilitySlot(
        @SerialName("ability") val ability: ResourcePointer<Ability>,
        @SerialName("slot") val slot: Int,
        @SerialName("is_hidden") val isHidden: Boolean,
    ) : HasResourcePinter<Ability> {

        override val pointer: ResourcePointer<Ability> = ability
    }

    @Serializable
    data class GameIndexSlot(
        @SerialName("game_index") val gameIndex: Int,
        @SerialName("version") val version: ResourcePointer<Version>,
    ) : HasResourcePinter<Version> {

        override val pointer: ResourcePointer<Version> = version
    }

    @Serializable
    data class HeldItemSlot(
        @SerialName("item") val item: ResourcePointer<Item>,
        @SerialName("version_details") val versionDetails: List<VersionDetails>,
    ) : HasResourcePinter<Item> {

        override val pointer: ResourcePointer<Item> = item

        @Serializable
        data class VersionDetails(
            @SerialName("rarity") val rarity: Int,
            @SerialName("version") val version: ResourcePointer<Version>,
        ) : HasResourcePinter<Version> {

            override val pointer: ResourcePointer<Version> = version
        }
    }

    @Serializable
    data class MoveSlot(
        @SerialName("move") val move: ResourcePointer<Move>,
        @SerialName("version_group_details") val versionGroupDetails: List<VersionGroupDetails>
    ) : HasResourcePinter<Move> {

        override val pointer: ResourcePointer<Move> = move

        @Serializable
        data class VersionGroupDetails(
            @SerialName("level_learned_at") val levelLearnedAt: Int,
            @SerialName("move_learn_method") val moveLearnMethod: ResourcePointer<MoveLearnMethod>,
            @SerialName("version_group") val versionGroup: ResourcePointer<VersionGroup>
        ) : HasResourcePinter<MoveLearnMethod> {

            override val pointer: ResourcePointer<MoveLearnMethod> = moveLearnMethod
        }
    }

    @Serializable
    data class StatSlot(
        @SerialName("base_stat") val baseStat: Int,
        @SerialName("effort") val effort: Int,
        @SerialName("stat") val stat: ResourcePointer<Stat>
    ): HasResourcePinter<Stat> {

        override val pointer: ResourcePointer<Stat> = stat
    }
}
