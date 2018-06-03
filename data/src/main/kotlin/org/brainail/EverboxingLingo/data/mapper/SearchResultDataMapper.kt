package org.brainail.EverboxingLingo.data.mapper

import org.brainail.EverboxingLingo.data.model.SearchResultEntity
import org.brainail.EverboxingLingo.domain.model.SearchResult
import javax.inject.Inject

/**
 * Map a [SearchResultEntity] to and from a [SearchResult] instance when data is passing between
 * this layer and the domain layer
 */
class SearchResultDataMapper @Inject constructor(): Mapper<SearchResultEntity, SearchResult> {
    override fun mapFromEntity(input: SearchResultEntity): SearchResult {
        TODO("No-impl")
    }

    override fun mapToEntity(input: SearchResult): SearchResultEntity {
        TODO("No-impl")
    }
}