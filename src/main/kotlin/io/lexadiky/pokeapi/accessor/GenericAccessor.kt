package io.lexadiky.pokeapi.accessor

import io.lexadiky.pokeapi.entity.common.HasResourcePointer
import io.lexadiky.pokeapi.entity.common.PagingPointer
import io.lexadiky.pokeapi.entity.common.ResourceList
import io.lexadiky.pokeapi.entity.common.ResourcePointer

/**
 * Generic way to access REST resource
 */
interface GenericAccessor<Resource> {

    /**
     * Name of resource this accessor belongs.
     * Corresponds to [Resource]
     */
    val name: String

    /**
     * Gives access to blocking version of accessor methods
     */
    val blocking: GenericBlockingAccessor<Resource>

    /**
     * Lists all available resources
     *
     * @return list of all resources
     */
    suspend fun all(): Result<ResourceList<Resource>>

    /**
     * Lists resources in [range]
     *
     * @param range range of resources to fetch
     * @return list of resources in [range]
     */
    suspend fun range(range: IntRange): Result<ResourceList<Resource>>

    /**
     * Gets resource details by [id]
     *
     * @param id of resource
     * @return resource details
     */
    suspend fun get(id: Int): Result<Resource>

    /**
     * Gets resource details by [name]
     *
     * @param name of resource
     * @return resource details
     */
    suspend fun get(name: String): Result<Resource>

    /**
     * Gets resource details by [ResourcePointer]. [ResourcePointer] could be retrieved by [all] or [range] methods. All taken from another resource.
     *
     * @param pointer to resource to retrieved
     * @return resource details
     */
    suspend fun get(pointer: ResourcePointer<Resource>): Result<Resource>

    /**
     * Gets resource details by [HasResourcePointer]. [HasResourcePointer] could be part of another resource
     *
     * @param pointer object containing explicit pointer to another resource
     * @return resource details
     */
    suspend fun get(pointer: HasResourcePointer<Resource>): Result<Resource>

    /**
     * Starts pagination with set [size] of page
     *
     * @param size of page
     * @return pagination object
     */
    fun pages(size: Int): Pages<Resource>

    /**
     * Pagination object with fixed page size
     */
    interface Pages<Resource> {

        /**
         * @return first page
         */
        suspend fun first(): Result<ResourceList<Resource>>

        /**
         * @return page by pointer to it, pointer could be found in previous [ResourceList]
         */
        suspend fun get(pointer: PagingPointer<Resource>): Result<ResourceList<Resource>>
    }
}
