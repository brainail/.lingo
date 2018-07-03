package org.brainail.EverboxingLingo.domain.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import org.brainail.EverboxingLingo.domain.model.Suggestion

interface SuggestionsRepository {
    fun clearSuggestions(): Completable
    fun saveSuggestions(suggestions: List<Suggestion>): Completable
    fun getSuggestions(query: String, limit: Int = Int.MAX_VALUE): Flowable<List<Suggestion>>
    fun getRecentSuggestions(query: String, limit: Int = Int.MAX_VALUE): Flowable<List<Suggestion>>
    fun saveSuggestionAsRecent(suggestion: Suggestion): Completable
}