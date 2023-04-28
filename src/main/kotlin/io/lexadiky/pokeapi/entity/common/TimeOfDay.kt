package io.lexadiky.pokeapi.entity.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class TimeOfDay {
    @SerialName("day")
    DAY,

    @SerialName("night")
    NIGHT,

    @SerialName("")
    ANY
}
