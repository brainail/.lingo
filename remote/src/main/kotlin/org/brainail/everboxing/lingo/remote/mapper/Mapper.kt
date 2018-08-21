package org.brainail.everboxing.lingo.remote.mapper

/**
 * Interface for model mappers.
 */
interface Mapper<in E, out D> {
    fun mapFromRemote(input: E): D
}