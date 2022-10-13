# PokeApi Kotlin
[![.github/workflows/build.yml](https://github.com/lexa-diky/pokeapi-kotlin/actions/workflows/build.yml/badge.svg)](https://github.com/lexa-diky/pokeapi-kotlin/actions/workflows/build.yml)
[![](https://jitpack.io/v/lexa-diky/pokeapi-kotlin.svg)](https://jitpack.io/#lexa-diky/pokeapi-kotlin)

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

Step 3. Use `io.lexadiky.pokeapi.PokeApiClient` to access powerful API
```kotlin
fun main() = runBlocking {
    val client = PokeApiClient { }
    val bulbasaur = client.pokemon.get("bulbasaur")
}
```

## Progress
✅ Basic API access via `get` and `all`

✅ Endpoint customization

🚧 All endpoints accessible

🚧 Fluid API

🚧 Documentation

🚧 Http client customization

🚧 Maven Central Artifact