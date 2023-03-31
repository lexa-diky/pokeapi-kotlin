package io.lexadiky.pokeapi.entity.characteristic

import io.lexadiky.pokeapi.entity.common.ResourcePointer
import io.lexadiky.pokeapi.entity.language.Language
import io.lexadiky.pokeapi.entity.stat.Stat
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Characteristic(
    @SerialName("id") val id: Int,
    @SerialName("descriptions") val descriptions: List<Description>,
    @SerialName("gene_modulo") val geneModulo: Int,
    @SerialName("highest_stat") val highestStat: ResourcePointer<Stat>,
    @SerialName("possible_values") val possibleValues: List<Int>
) {

    @Serializable
    data class Description(
        @SerialName("description") val description: String,
        @SerialName("language") val language: ResourcePointer<Language>
    )
}
