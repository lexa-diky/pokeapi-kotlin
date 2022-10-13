package io.lexadiky.pokeapi.entity.common

interface HasResourcePinter<T> {

    val pointer: ResourcePointer<T>
}