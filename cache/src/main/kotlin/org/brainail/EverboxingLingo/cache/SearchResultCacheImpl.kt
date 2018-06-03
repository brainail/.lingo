package org.brainail.EverboxingLingo.cache

import io.reactivex.Completable
import io.reactivex.Flowable
import org.brainail.EverboxingLingo.cache.mapper.SearchResultCacheMapper
import org.brainail.EverboxingLingo.data.model.SearchResultEntity
import org.brainail.EverboxingLingo.data.repository.search.SearchResultCache
import javax.inject.Inject

class SearchResultCacheImpl @Inject constructor(
        private val entityMapper: SearchResultCacheMapper) : SearchResultCache {

    override fun clearSearchResults(): Completable {
        TODO("No-impl")
    }

    override fun saveSearchResults(searchResults: List<SearchResultEntity>): Completable {
        return Completable.defer {
            // ninja
            Completable.complete()
        }
    }

    override fun getSearchResults(query: String): Flowable<List<SearchResultEntity>> {
        TODO("No-impl")
    }

    override fun isCached(): Boolean {
        TODO("No-impl")
    }

    override fun isExpired(): Boolean {
        TODO("No-impl")
    }
}