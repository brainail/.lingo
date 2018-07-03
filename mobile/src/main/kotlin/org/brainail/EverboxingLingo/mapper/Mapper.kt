package org.brainail.EverboxingLingo.mapper

/**
 * Interface for model mappers.
 */
interface Mapper<D, V> {
    fun mapToModel(input: D): V
    fun mapFromModel(input: V): D
}