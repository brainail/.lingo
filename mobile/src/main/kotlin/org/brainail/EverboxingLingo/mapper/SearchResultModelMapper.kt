package org.brainail.EverboxingLingo.mapper

import org.brainail.EverboxingLingo.domain.model.SearchResult
import org.brainail.EverboxingLingo.model.SearchResultModel
import javax.inject.Inject

class SearchResultModelMapper @Inject constructor(): Mapper<SearchResult, SearchResultModel> {
    override fun mapFromModel(input: SearchResultModel): SearchResult {
        return SearchResult(input.id, input.definitionId,
                input.word, input.definition, input.example, input.link)
    }

    override fun mapToModel(input: SearchResult): SearchResultModel {
        return SearchResultModel(input.id, input.definitionId,
                input.word, input.definition, input.example, input.link)
    }
}