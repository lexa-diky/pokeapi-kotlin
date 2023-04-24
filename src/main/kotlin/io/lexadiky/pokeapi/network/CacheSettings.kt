package io.lexadiky.pokeapi.network

import java.io.File

/**
 * Specifies how client will store request cache
 */
sealed interface CacheSettings {

    /**
     * Requests will not be cached
     */
    object Disabled: CacheSettings

    /**
     * Requests will be cached on disc at [directory]
     */
    data class FileStorage(val directory: File): CacheSettings
}
