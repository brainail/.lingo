package org.brainail.EverboxingLingo.mapper

/**
 * Interface for model mappers.
 */
interface Mapper<in D, out V> {
    fun mapToModel(input: D): V
}