@file:UseSerializers(PagingPointerSerializer::class)

package io.lexadiky.pokeapi.entity.common

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * Wraps pagination response
 */
@Serializable
data class ResourceList<Resource>(
    @SerialName("count") val count: Int,
    @SerialName("next") val next: PagingPointer<Resource>?,
    @SerialName("previous") val previous: PagingPointer<Resource>?,
    @SerialName("results") val results: List<ResourcePointer<Resource>>
): List<ResourcePointer<Resource>> by results

/**
 * Pointer to another page. Keeps type safety when requesting next page
 */
@JvmInline
@Suppress("unused")
value class PagingPointer<Resource>(val url: String)

/**
 * Serializer [String] <-> [PagingPointer]
 */
private class PagingPointerSerializer : KSerializer<PagingPointer<Any?>> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("paging_pointer", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): PagingPointer<Any?> {
        return PagingPointer(decoder.decodeString())
    }

    override fun serialize(encoder: Encoder, value: PagingPointer<Any?>) {
        return encoder.encodeString(value.url)
    }
}
