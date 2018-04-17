package org.brainail.EverboxingLingo.data.repository.suggestion

import io.reactivex.Completable
import io.reactivex.Single
import org.brainail.EverboxingLingo.data.model.SuggestionEntity

interface SuggestionDataSource {
    fun clearSuggestions(): Completable
    fun saveSuggestions(suggestions: List<SuggestionEntity>): Completable
    fun getSuggestions(query: String): Single<List<SuggestionEntity>>
}