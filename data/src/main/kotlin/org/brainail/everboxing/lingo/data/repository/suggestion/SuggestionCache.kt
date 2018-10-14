package org.brainail.everboxing.lingo.data.repository.suggestion

import io.reactivex.Completable
import io.reactivex.Flowable
import org.brainail.everboxing.lingo.data.model.SuggestionEntity

interface SuggestionCache {
    fun clearSuggestions(): Completable
    fun saveSuggestions(suggestions: List<SuggestionEntity>): Completable
    fun getSuggestions(query: String, limit: Int = Int.MAX_VALUE): Flowable<List<SuggestionEntity>>
    fun getRecentSuggestions(query: String, limit: Int = Int.MAX_VALUE): Flowable<List<SuggestionEntity>>
    @JvmDefault
    fun isCached(): Boolean = false
    @JvmDefault
    fun isExpired(): Boolean = true
}