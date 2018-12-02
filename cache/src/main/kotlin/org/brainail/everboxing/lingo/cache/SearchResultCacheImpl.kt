package org.brainail.everboxing.lingo.cache

import io.reactivex.Completable
import io.reactivex.Flowable
import org.brainail.everboxing.lingo.cache.db.dao.SearchResultDao
import org.brainail.everboxing.lingo.cache.mapper.SearchResultCacheMapper
import org.brainail.everboxing.lingo.cache.mapper.SearchResultInstallDataMapper
import org.brainail.everboxing.lingo.data.model.SearchResultEntity
import org.brainail.everboxing.lingo.data.repository.file.FileStore
import org.brainail.everboxing.lingo.data.repository.search.SearchResultCache
import org.brainail.everboxing.lingo.data.util.DatabaseTransactionRunner
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchResultCacheImpl @Inject constructor(
        private val searchResultDao: SearchResultDao,
        private val fileStore: FileStore,
        private val searchResultInstallDataMapper: SearchResultInstallDataMapper,
        private val searchResultCacheMapper: SearchResultCacheMapper,
        private val databaseTransactionRunner: DatabaseTransactionRunner) : SearchResultCache {

    override fun clearSearchResults(): Completable {
        return Completable.defer {
            searchResultDao.deleteAll()
            Completable.complete()
        }
    }

    override fun saveSearchResults(searchResults: List<SearchResultEntity>): Completable {
        return Completable.defer {
            searchResultDao.insert(searchResults.map { searchResultCacheMapper.mapToCache(it) })
            Completable.complete()
        }
    }

    override fun getSearchResults(query: String): Flowable<List<SearchResultEntity>> {
        return searchResultDao.getSearchResults(query).map { it.map { searchResultCacheMapper.mapFromCache(it) } }
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

    override fun installUrbanSearchResult(pathToData: String): Completable {
        return Completable.defer {
            databaseTransactionRunner.invoke {
                fileStore.openAsset(pathToData).bufferedReader(Charsets.UTF_8).useLines {
                    it.forEach {
                        val entity = searchResultInstallDataMapper.mapToCache(it)
                        searchResultDao.insert(entity)
                    }
                }
            }
            Completable.complete()
        }
    }
}
