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
        return SearchResult(input.id, input.definitionId,
                input.word, input.definition, input.example, input.link)
    }

    override fun mapToEntity(input: SearchResult): SearchResultEntity {
        return SearchResultEntity(input.definitionId,
                input.word, input.definition, input.example, input.link, input.id)
    }
}