package io.lexadiky.pokeapi

import io.lexadiky.pokeapi.accessor.GenericAccessor
import io.lexadiky.pokeapi.network.CacheSettings
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.DynamicContainer
import org.junit.jupiter.api.DynamicContainer.dynamicContainer
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.parallel.Execution
import org.junit.jupiter.api.parallel.ExecutionMode
import java.util.stream.Stream
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.isSubtypeOf
import kotlin.reflect.typeOf
import kotlin.time.Duration.Companion.seconds

class IntegrationGrandTest {

    @TestFactory
    fun runSpecific(): Stream<DynamicContainer> {
        val endpoint = "move"

        val client = PokeApiClient {
            timeout = 10.seconds // if we throttle, do nothing
            cache = CacheSettings.Disabled // do not cache
        }


        return extractAccessors(client).stream()
            .filter { it.name == endpoint }
            .map { test(it, 1, 1) }
    }

    @TestFactory
    @DisplayName("pull all data")
    @Execution(ExecutionMode.CONCURRENT)
    fun factory(): Stream<DynamicContainer> {
        val client = PokeApiClient {
            timeout = 10.seconds // if we throttle, do nothing
            cache = CacheSettings.Disabled // do not cache
        }

        val accessors = extractAccessors(client)

        return accessors.withIndex().toList().stream()
            .map { (idx, accessor) -> test(accessor, accessors.size, idx) }
    }

    private fun test(accessor: GenericAccessor<Any>, totalSize: Int, idx: Int): DynamicContainer {
        val blocking = accessor.blocking
        val allElements = blocking.all().getOrThrow()
        val allIndexed = allElements.withIndex().toList().stream()

        return dynamicContainer(
            "get all data from ${accessor.name} [${idx + 1}/$totalSize]",
            allIndexed.map { (idx, pointer) ->
                dynamicTest("get $pointer [${idx + 1}/${allElements.size}]") {
                    blocking.get(pointer).getOrThrow()
                }
            }
        )
    }

    private fun extractAccessors(client: PokeApiClient): List<GenericAccessor<Any>> {
        val clientKClass = client::class
        return clientKClass.declaredMemberProperties
            .filter { it.returnType.isSubtypeOf(typeOf<GenericAccessor<*>>()) }
            .filterIsInstance<KProperty1<PokeApiClient, GenericAccessor<Any>>>()
            .map { it.get(client) }
    }
}
