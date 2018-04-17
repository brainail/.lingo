package org.brainail.EverboxingLingo.data.source.suggestion

import io.reactivex.Completable
import io.reactivex.Single
import org.brainail.EverboxingLingo.data.model.SuggestionEntity
import org.brainail.EverboxingLingo.data.repository.suggestion.SuggestionDataSource
import org.brainail.EverboxingLingo.data.repository.suggestion.SuggestionRemote
import javax.inject.Inject

class SuggestionsRemoteDataSource @Inject constructor(
        private val suggestionRemote: SuggestionRemote): SuggestionDataSource {

    override fun clearSuggestions(): Completable {
        throw UnsupportedOperationException()
    }

    override fun saveSuggestions(suggestions: List<SuggestionEntity>): Completable {
        throw UnsupportedOperationException()
    }

    override fun getSuggestions(query: String): Single<List<SuggestionEntity>> {
        return suggestionRemote.getSuggestions(query)
    }
}