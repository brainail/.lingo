package org.brainail.EverboxingLingo.data.repository.suggestion

import io.reactivex.Completable
import io.reactivex.Flowable
import org.brainail.EverboxingLingo.data.model.SuggestionEntity

interface SuggestionDataSource {
    fun clearSuggestions(): Completable
    fun saveSuggestions(suggestions: List<SuggestionEntity>): Completable
    fun getSuggestions(query: String, limit: Int = Int.MAX_VALUE): Flowable<List<SuggestionEntity>>
    fun getRecentSuggestions(query: String, limit: Int = Int.MAX_VALUE): Flowable<List<SuggestionEntity>>
}