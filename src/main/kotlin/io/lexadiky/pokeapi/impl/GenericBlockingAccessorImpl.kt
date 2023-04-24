package io.lexadiky.pokeapi.impl

import io.lexadiky.pokeapi.accessor.GenericAccessor
import io.lexadiky.pokeapi.accessor.GenericBlockingAccessor
import io.lexadiky.pokeapi.entity.common.HasResourcePointer
import io.lexadiky.pokeapi.entity.common.PagingPointer
import io.lexadiky.pokeapi.entity.common.ResourceList
import io.lexadiky.pokeapi.entity.common.ResourcePointer
import kotlinx.coroutines.runBlocking

internal class GenericBlockingAccessorImpl<Resource>(
    private val genericAccessor: GenericAccessor<Resource>
) : GenericBlockingAccessor<Resource> {

    override fun all(): Result<ResourceList<Resource>> = runBlocking{
        genericAccessor.all()
    }

    override fun get(id: Int): Result<Resource> = runBlocking {
        genericAccessor.get(id)
    }

    override fun get(name: String): Result<Resource> = runBlocking {
        genericAccessor.get(name)
    }

    override fun get(pointer: HasResourcePointer<Resource>): Result<Resource> = runBlocking {
        genericAccessor.get(pointer)
    }

    override fun get(pointer: ResourcePointer<Resource>): Result<Resource> = runBlocking {
        genericAccessor.get(pointer)
    }

    override fun pages(size: Int): GenericBlockingAccessor.Pages<Resource> {
        return Pages(genericAccessor.pages(size))
    }

    override fun range(range: IntRange): Result<ResourceList<Resource>> = runBlocking {
        genericAccessor.range(range)
    }

    class Pages<Resource>(
        private val pages: GenericAccessor.Pages<Resource>
        ) : GenericBlockingAccessor.Pages<Resource> {

        override fun first(): Result<ResourceList<Resource>> = runBlocking {
            pages.first()
        }

        override fun get(pointer: PagingPointer<Resource>): Result<ResourceList<Resource>> = runBlocking {
            pages.get(pointer)
        }
    }
}
