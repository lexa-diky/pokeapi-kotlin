package io.lexadiky.pokeapi.util

import java.io.File

sealed interface CacheSettings {

    object Disabled: CacheSettings

    data class FileStorage(val directory: File): CacheSettings
}