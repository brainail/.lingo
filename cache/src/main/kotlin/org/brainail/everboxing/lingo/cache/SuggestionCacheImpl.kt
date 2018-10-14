package org.brainail.everboxing.lingo.cache

import io.reactivex.Completable
import io.reactivex.Flowable
import org.brainail.everboxing.lingo.cache.db.dao.SuggestionDao
import org.brainail.everboxing.lingo.cache.mapper.SuggestionCacheMapper
import org.brainail.everboxing.lingo.data.model.SuggestionEntity
import org.brainail.everboxing.lingo.data.repository.suggestion.SuggestionCache
import javax.inject.Inject

class SuggestionCacheImpl @Inject constructor(
        private val suggestionDao: SuggestionDao,
        private val entityMapper: SuggestionCacheMapper) : SuggestionCache {

    override fun clearSuggestions(): Completable {
        return Completable.defer {
            // completing ...
            Completable.complete()
        }
    }

    override fun saveSuggestions(suggestions: List<SuggestionEntity>): Completable {
        return Completable.defer {
            suggestionDao.saveSuggestions(suggestions.map { entityMapper.mapToCache(it) })
            Completable.complete()
        }
    }

    override fun getSuggestions(query: String, limit: Int): Flowable<List<SuggestionEntity>> {
        return suggestionDao.getSuggestions(query, limit).map { it.map { entityMapper.mapFromCache(it) } }
    }

    override fun getRecentSuggestions(query: String, limit: Int): Flowable<List<SuggestionEntity>> {
        return suggestionDao.getRecentSuggestions(query, limit).map { it.map { entityMapper.mapFromCache(it) } }
    }
}