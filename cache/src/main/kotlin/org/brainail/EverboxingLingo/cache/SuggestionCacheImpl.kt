package org.brainail.EverboxingLingo.cache

import io.reactivex.Completable
import io.reactivex.Single
import org.brainail.EverboxingLingo.cache.mapper.SuggestionCacheMapper
import org.brainail.EverboxingLingo.data.model.SuggestionEntity
import org.brainail.EverboxingLingo.data.repository.suggestion.SuggestionCache
import javax.inject.Inject

class SuggestionCacheImpl @Inject constructor(
        private val entityMapper: SuggestionCacheMapper) : SuggestionCache {

    override fun clearSuggestions(): Completable {
        TODO("No-impl")
    }

    override fun saveSuggestions(suggestions: List<SuggestionEntity>): Completable {
        return Completable.defer {
            // ninja
            Completable.complete()
        }
    }

    override fun getSuggestions(query: String): Single<List<SuggestionEntity>> {
        TODO("No-impl")
    }
}