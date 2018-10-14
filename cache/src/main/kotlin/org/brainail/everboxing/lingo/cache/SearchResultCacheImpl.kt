package org.brainail.everboxing.lingo.cache

import io.reactivex.Completable
import io.reactivex.Flowable
import org.brainail.everboxing.lingo.cache.db.dao.SearchResultDao
import org.brainail.everboxing.lingo.cache.mapper.SearchResultCacheMapper
import org.brainail.everboxing.lingo.data.model.SearchResultEntity
import org.brainail.everboxing.lingo.data.repository.search.SearchResultCache
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchResultCacheImpl @Inject constructor(
        private val searchResultDao: SearchResultDao,
        private val entityMapper: SearchResultCacheMapper) : SearchResultCache {

    override fun clearSearchResults(): Completable {
        return Completable.defer {
            searchResultDao.deleteAll()
            Completable.complete()
        }
    }

    override fun saveSearchResults(searchResults: List<SearchResultEntity>): Completable {
        return Completable.defer {
            searchResultDao.insert(searchResults.map { entityMapper.mapToCache(it) })
            Completable.complete()
        }
    }

    override fun getSearchResults(query: String): Flowable<List<SearchResultEntity>> {
        return searchResultDao.getSearchResults(query).map { it.map { entityMapper.mapFromCache(it) } }
    }

    override fun favoriteSearchResult(id: Int): Completable {
        return Completable.defer {
            searchResultDao.favoriteSearchResult(id)
            Completable.complete()
        }
    }

    override fun forgetSearchResult(id: Int): Completable {
        return Completable.defer {
            searchResultDao.forgetSearchResult(id)
            Completable.complete()
        }
    }
}