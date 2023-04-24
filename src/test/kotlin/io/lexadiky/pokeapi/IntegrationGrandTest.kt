package io.lexadiky.pokeapi

import io.lexadiky.pokeapi.accessor.GenericAccessor
import io.lexadiky.pokeapi.network.CacheSettings
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.DynamicContainer
import org.junit.jupiter.api.DynamicContainer.dynamicContainer
import org.junit.jupiter.api.DynamicTest.*
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
    @Disabled
    fun runSpecific(): Stream<DynamicContainer> {
        val client = PokeApiClient {
            timeout = 10.seconds // if we throttle, do nothing
            cache = CacheSettings.Disabled // do not cache
        }


        return extractAccessors(client)
            .filter { it.name == "move" }
            .map(::test)
    }

    @TestFactory
    @DisplayName("pull all data")
    @Execution(ExecutionMode.CONCURRENT)
    fun factory(): Stream<DynamicContainer> {
        val client = PokeApiClient {
            timeout = 10.seconds // if we throttle, do nothing
            cache = CacheSettings.Disabled // do not cache
        }


        return extractAccessors(client)
            .map(::test)
    }

    private fun test(accessor: GenericAccessor<Any>): DynamicContainer {
        val blocking = accessor.blocking
        val all = blocking.all().getOrThrow().stream()

        return dynamicContainer(
            "get all data from ${accessor.name}",
            all.map { pointer ->
                dynamicTest("get $pointer") {
                    blocking.get(pointer).getOrThrow()
                }
            }
        )
    }

    private fun extractAccessors(client: PokeApiClient): Stream<GenericAccessor<Any>> {
        val clientKClass = client::class
        return clientKClass.declaredMemberProperties
            .filter { it.returnType.isSubtypeOf(typeOf<GenericAccessor<*>>()) }
            .mapNotNull { it as? KProperty1<PokeApiClient, GenericAccessor<Any>> }
            .map { it.get(client) }
            .stream()
    }
}