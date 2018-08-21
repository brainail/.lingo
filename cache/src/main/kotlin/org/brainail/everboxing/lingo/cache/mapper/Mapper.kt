package org.brainail.everboxing.lingo.cache.mapper

/**
 * Interface for model mappers.
 */
interface Mapper<E, D> {
    fun mapFromCache(input: E): D
    fun mapToCache(input: D): E
}