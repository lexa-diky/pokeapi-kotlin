package io.lexadiky.pokeapi.accessor

import io.ktor.util.reflect.typeInfo
import io.lexadiky.pokeapi.entity.common.HasResourcePointer
import io.lexadiky.pokeapi.entity.common.PagingPointer
import io.lexadiky.pokeapi.entity.common.ResourceList
import io.lexadiky.pokeapi.entity.common.ResourcePointer
import io.lexadiky.pokeapi.impl.HttpRequester

/**
 * Generic way to access REST resource
 */
interface GenericBlockingAccessor<Resource> {

    /**
     * Lists all available resources
     *
     * @return list of all resources
     */
    fun all(): Result<ResourceList<Resource>>

    /**
     * Lists resources in [range]
     *
     * @param range range of resources to fetch
     * @return list of resources in [range]
     */
    fun range(range: IntRange): Result<ResourceList<Resource>>

    /**
     * Gets resource details by [id]
     *
     * @param id of resource
     * @return resource details
     */
    fun get(id: Int): Result<Resource>

    /**
     * Gets resource details by [name]
     *
     * @param name of resource
     * @return resource details
     */
    fun get(name: String): Result<Resource>

    /**
     * Gets resource details by [ResourcePointer]. [ResourcePointer] could be retrieved by [all] or [range] methods. All taken from another resource.
     *
     * @param pointer to resource to retrieved
     * @return resource details
     */
    fun get(pointer: ResourcePointer<Resource>): Result<Resource>

    /**
     * Gets resource details by [HasResourcePointer]. [HasResourcePointer] could be part of another resource
     *
     * @param pointer object containing explicit pointer to another resource
     * @return resource details
     */
    fun get(pointer: HasResourcePointer<Resource>): Result<Resource>

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
        fun first(): Result<ResourceList<Resource>>

        /**
         * @return page by pointer to it, pointer could be found in previous [ResourceList]
         */
        fun get(pointer: PagingPointer<Resource>): Result<ResourceList<Resource>>
    }
}
