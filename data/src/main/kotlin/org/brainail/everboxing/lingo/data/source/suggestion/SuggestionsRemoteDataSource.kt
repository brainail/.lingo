package org.brainail.everboxing.lingo.data.source.suggestion

import io.reactivex.Completable
import io.reactivex.Flowable
import org.brainail.everboxing.lingo.data.model.SuggestionEntity
import org.brainail.everboxing.lingo.data.repository.suggestion.SuggestionDataSource
import org.brainail.everboxing.lingo.data.repository.suggestion.SuggestionRemote
import javax.inject.Inject

class SuggestionsRemoteDataSource @Inject constructor(
        private val suggestionRemote: SuggestionRemote): SuggestionDataSource {

    override fun clearSuggestions(): Completable {
        throw UnsupportedOperationException()
    }

    override fun saveSuggestions(suggestions: List<SuggestionEntity>): Completable {
        throw UnsupportedOperationException()
    }

    override fun getSuggestions(query: String, limit: Int): Flowable<List<SuggestionEntity>> {
        return suggestionRemote.getSuggestions(query).map { it.take(limit) }.toFlowable()
    }

    override fun getRecentSuggestions(query: String, limit: Int): Flowable<List<SuggestionEntity>> {
        throw UnsupportedOperationException()
    }
}
