package io.lexadiky.pokeapi.entity.type

import io.lexadiky.pokeapi.entity.common.HasResourcePointer
import io.lexadiky.pokeapi.entity.common.Name
import io.lexadiky.pokeapi.entity.common.ResourcePointer
import io.lexadiky.pokeapi.entity.generation.Generation
import io.lexadiky.pokeapi.entity.move.Move
import io.lexadiky.pokeapi.entity.move.MoveDamageClass
import io.lexadiky.pokeapi.entity.pokemon.Pokemon
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Type(
    @SerialName("name") val name: String,
    @SerialName("id") val id: Int,
    @SerialName("pokemon") val pokemon: List<PokemonSlot>,
    @SerialName("game_indices") val gameIndices: List<GameIndexSlot>,
    @SerialName("generation") val generation: ResourcePointer<Generation>,
    @SerialName("moves") val moves: List<ResourcePointer<Move>>,
    @SerialName("names") val names: List<Name>,
    @SerialName("move_damage_class") val moveDamageClass: ResourcePointer<MoveDamageClass>?,
    @SerialName("damage_relations") val damageRelations: DamageRelations,
    @SerialName("past_damage_relations") val pastDamageRelations: List<PastDamageRelations>
) {

    @Serializable
    data class PokemonSlot(
        @SerialName("pokemon") val pokemon: ResourcePointer<Pokemon>,
        @SerialName("slot") val slot: Int,
    ) : HasResourcePointer<Pokemon> {

        override val pointer: ResourcePointer<Pokemon> = pokemon
    }

    @Serializable
    data class GameIndexSlot(
        @SerialName("game_index") val gameIndex: Int,
        @SerialName("generation") val version: ResourcePointer<Generation>,
    ) : HasResourcePointer<Generation> {

        override val pointer: ResourcePointer<Generation> = version
    }

    @Serializable
    data class DamageRelations(
        @SerialName("double_damage_from") val doubleDamageFrom: List<ResourcePointer<Type>>,
        @SerialName("double_damage_to") val doubleDamageTo: List<ResourcePointer<Type>>,
        @SerialName("half_damage_from") val halfDamageFrom: List<ResourcePointer<Type>>,
        @SerialName("half_damage_to") val halfDamageTo: List<ResourcePointer<Type>>,
        @SerialName("no_damage_from") val noDamageFrom: List<ResourcePointer<Type>>,
        @SerialName("no_damage_to") val noDamageTo: List<ResourcePointer<Type>>,
    )

    @Serializable
    data class PastDamageRelations(
        @SerialName("damage_relations")
        val damageRelations: DamageRelations,
        @SerialName("generation")
        val generation: ResourcePointer<Generation>
    )
}
