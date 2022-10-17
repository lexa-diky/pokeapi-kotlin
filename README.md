# PokeApi Kotlin

[![.github/workflows/build.yml](https://github.com/lexa-diky/pokeapi-kotlin/actions/workflows/build.yml/badge.svg)](https://github.com/lexa-diky/pokeapi-kotlin/actions/workflows/build.yml)
[![](https://jitpack.io/v/lexa-diky/pokeapi-kotlin.svg)](https://jitpack.io/#lexa-diky/pokeapi-kotlin)
[![](https://img.shields.io/badge/API%20Reference-Dokka-blue)](https://lexa-diky.github.io/pokeapi-kotlin/)


Kotlin client for https://pokeapi.co powered by coroutines.

## Features

1. Full https://pokeapi.co REST API coverage (WIP)
2. Caching out of the box
3. Powered by coroutines
4. No exceptions, everything wrapped into Result<T> out of the box
4. Both 1-1 with original API layer and enhanced 'fluid' version
5. No transitive dependencies
6. Ability to customize underlying HTTP engine (WIP)
7. Ability to use custom PokeApi deployment

## Get Started

To get a Git project into your build:

Step 1. Add the JitPack repository to your build file

```kotlin
allprojects {
    repositories {
        maven { setUrl("https://jitpack.io") }
    }
}
```

Step 2. Add the dependency

```kotlin
dependencies {
    implementation("com.github.lexa-diky:pokeapi-kotlin:-SNAPSHOT")
}
```

Step 3. Use `PokeApiClient` to access powerful API

```kotlin
val client = PokeApiClient { }
val bulbasaur = client.pokemon.get("bulbasaur")

// or blocking call

val pikachu = client.pokemon.blocking.get("pikachu")
```

## API

### Normal

To access particular resource this library provides so called `GenericAccessor` objects. These accessors are defined in `PokeApiClient` class (for
example `client.pokemon`).

There are 2 kinds of methods:

#### Resource List

1. `all` - returns full list of resources available in accessor
2. `range` - returns list of resources available in accessor in passed `IntRange`

Both methods return `ResourceList` object containing `results` list of `ResourcePointer` to resource details and
`next`, `previous` and `count` for paging.

#### Resource Details

`get` - polymorphic method accepting either `id: Int`, `name: String`, `pointer: ResourcePointer`, `pointer: HasResourcePointer` to an resource.
Will return details of resource with passed identifier

### Paging

There are 2 ways to implement paging resource access via this library.

1. You can use `range` method on any `GenericAccessor` accepting `IntRange` of object ids.
2. Or you can use specialized paging api via `pages` method.

First approach is a bit more manual, you have to manage page size and id ranges manually.

Second approach is simpler. `pages` accepts size of requested page and returns `Paging` object with
two methods: `first` - returns first page and `get` - returns page by pointer from previous request. For example:

```kotlin
val client = PokeApiClient { }
val pages = client.pokemon.pages(size = 10)
val firstPage = pages.first().getOrThrow()
val secondPage = pages.get(firstPage.next!!).getOrThrow()
val firstPageAgain = pages.get(secondPage.previous!!)
```

### Blocking

Blocking API methods could be accessed via 'blocking' property on all accessors.

Blocking methods are 1-1 copy of `suspended` ones, all async documentation applicable.

```kotlin
val client = PokeApiClient { }
val pikachu = client.pokemon.blocking.get("pikachu")
```

### Fluid

Fluid API uses mostly the same methods as normal one, but all `ResourcePointer<T>` objects now have
`get` method returning pointed object

```kotlin
val client = PokeApiClient { }
val allPokemonWithFireType = client.use {
    pokemon.all().get().filter { pkmn ->
        pkmn.get().types.map { it.type.get() }
            .any { it.name == "fire" }
    }
}
```

## Progress

✅ Basic API access via `get` and `all`

✅ Endpoint customization

✅ Java friendly blocking calls

✅ Type safe resource access via references in other resources

✅ Fluid API

✅ Documentation

🚧 All endpoints accessible

🚧 Optional integration with Kotlin Arrow library

🚧 Http client customization

🚧 Maven Central Artifact