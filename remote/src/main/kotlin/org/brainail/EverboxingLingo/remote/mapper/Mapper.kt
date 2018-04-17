package org.brainail.EverboxingLingo.remote.mapper

/**
 * Interface for model mappers.
 */
interface Mapper<in E, out D> {
    fun mapFromRemote(input: E): D
}