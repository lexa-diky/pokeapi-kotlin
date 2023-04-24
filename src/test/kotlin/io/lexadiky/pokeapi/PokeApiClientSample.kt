package io.lexadiky.pokeapi

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

@Suppress("UNUSED_VARIABLE")
class PokeApiClientSample {

    @[Disabled Test]
    fun `setup for custom api endpoint`() {
        val client = PokeApiClient {
            host = "lexadiky.io"
            path = "pokeapi/api/v2/"
        }
    }
}
