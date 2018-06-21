package org.brainail.EverboxingLingo.data.source.suggestion

import io.reactivex.Completable
import io.reactivex.Single
import org.brainail.EverboxingLingo.data.model.SuggestionEntity
import org.brainail.EverboxingLingo.data.repository.suggestion.SuggestionCache
import org.brainail.EverboxingLingo.data.repository.suggestion.SuggestionDataSource
import javax.inject.Inject

class SuggestionsCacheDataSource @Inject constructor(
        private val suggestionCache: SuggestionCache): SuggestionDataSource {

    override fun clearSuggestions(): Completable {
        return suggestionCache.clearSuggestions()
    }

    override fun saveSuggestions(suggestions: List<SuggestionEntity>): Completable {
        return suggestionCache.saveSuggestions(suggestions)
    }

    override fun getSuggestions(query: String): Single<List<SuggestionEntity>> {
        return suggestionCache.getSuggestions(query)
    }
}