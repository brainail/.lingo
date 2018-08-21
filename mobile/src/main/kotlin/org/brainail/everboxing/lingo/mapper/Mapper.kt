package org.brainail.everboxing.lingo.mapper

/**
 * Interface for model mappers.
 */
interface Mapper<D, V> {
    fun mapToModel(input: D): V
    fun mapFromModel(input: V): D
}