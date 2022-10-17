package io.lexadiky.pokeapi.entity.stat

import io.lexadiky.pokeapi.entity.characteristic.Characteristic
import io.lexadiky.pokeapi.entity.common.HasResourcePointer
import io.lexadiky.pokeapi.entity.common.Name
import io.lexadiky.pokeapi.entity.common.ResourcePointer
import io.lexadiky.pokeapi.entity.move.Move
import io.lexadiky.pokeapi.entity.move.MoveDamageClass
import io.lexadiky.pokeapi.entity.pokemon.Nature
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Stat(
    @SerialName("name") val name: String,
    @SerialName("id") val id: Int,
    @SerialName("game_index") val gameIndex: Int,
    @SerialName("is_battle_only") val isBattleOnly: Boolean,
    @SerialName("affecting_moves") val affectingMoves: AffectingMoves,
    @SerialName("affecting_natures") val affectingNatures: AffectingNatures,
    @SerialName("characteristics") val characteristics: List<ResourcePointer<Characteristic>>,
    @SerialName("move_damage_class") val moveDamageClass: ResourcePointer<MoveDamageClass>?,
    @SerialName("names") val names: List<Name>
) {

    @Serializable
    data class AffectingNatures(
        @SerialName("decrease") val decrease: List<ResourcePointer<Nature>>,
        @SerialName("increase") val increase: List<ResourcePointer<Nature>>
    )

    @Serializable
    data class AffectingMoves(
        @SerialName("decrease") val decrease: List<Entry>,
        @SerialName("increase") val increase: List<Entry>
    ) {

        @Serializable
        data class Entry(
            @SerialName("change") val change: Int,
            @SerialName("move") val move: ResourcePointer<Move>
        ): HasResourcePointer<Move> {

            override val pointer: ResourcePointer<Move> = move
        }
    }
}
