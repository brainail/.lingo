package org.brainail.EverboxingLingo.data.mapper

/**
 * Interface for model mappers.
 */
interface Mapper<E, D> {
    fun mapFromEntity(input: E): D
    fun mapToEntity(input: D): E
}