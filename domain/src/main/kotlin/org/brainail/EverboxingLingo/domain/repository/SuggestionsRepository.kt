package org.brainail.EverboxingLingo.domain.repository

import io.reactivex.Completable
import io.reactivex.Single
import org.brainail.EverboxingLingo.domain.model.Suggestion

interface SuggestionsRepository {
    fun clearSuggestions(): Completable
    fun saveSuggestions(suggestions: List<Suggestion>): Completable
    fun getSuggestions(query: String): Single<List<Suggestion>>
}