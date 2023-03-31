package io.lexadiky.pokeapi.entity.common

/**
 * Denotes that object has explicit singular [ResourcePointer] to resource it represents
 */
interface HasResourcePointer<T> {

    val pointer: ResourcePointer<T>
}
