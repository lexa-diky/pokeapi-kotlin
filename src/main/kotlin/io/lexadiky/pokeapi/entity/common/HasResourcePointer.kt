package io.lexadiky.pokeapi.entity.common

interface HasResourcePointer<T> {

    val pointer: ResourcePointer<T>
}