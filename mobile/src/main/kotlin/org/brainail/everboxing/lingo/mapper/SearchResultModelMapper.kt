package org.brainail.everboxing.lingo.mapper

import org.brainail.everboxing.lingo.domain.model.SearchResult
import org.brainail.everboxing.lingo.model.SearchResultModel
import javax.inject.Inject

class SearchResultModelMapper @Inject constructor(): Mapper<SearchResult, SearchResultModel> {
    override fun mapFromModel(input: SearchResultModel): SearchResult {
        return SearchResult(input.id, input.definitionId,
                input.word, input.definition, input.example, input.link, input.favorite)
    }

    override fun mapToModel(input: SearchResult): SearchResultModel {
        return SearchResultModel(input.id, input.definitionId,
                input.word, input.definition, input.example, input.link, input.favorite)
    }
}
