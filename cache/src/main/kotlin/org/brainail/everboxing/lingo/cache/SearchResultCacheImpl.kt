/*
 * Copyright 2018 Malyshev Yegor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
    private val databaseTransactionRunner: DatabaseTransactionRunner
) : SearchResultCache {

    override fun saveSearchResults(searchResults: List<SearchResultEntity>): Completable {
        return Completable.defer {
            searchResultDao.insert(searchResults.map { searchResultCacheMapper.mapT(it) })
            Completable.complete()
        }
    }

    override fun getSearchResults(query: String, limit: Int): Flowable<List<SearchResultEntity>> {
        return searchResultDao
            .getSearchResults(query, limit)
            .map { it.map { searchResult -> searchResultCacheMapper.mapF(searchResult) } }
    }

    override fun getFavoriteSearchResults(query: String, limit: Int): Flowable<List<SearchResultEntity>> {
        return searchResultDao
            .getFavoriteSearchResults(query, limit)
            .map { it.map { searchResult -> searchResultCacheMapper.mapF(searchResult) } }
    }

    override fun getHistorySearchResults(query: String, limit: Int): Flowable<List<SearchResultEntity>> {
        return searchResultDao
            .getHistorySearchResults(query, limit)
            .map { it.map { searchResult -> searchResultCacheMapper.mapF(searchResult) } }
    }

    override fun getDistinctByWordSearchResults(query: String, limit: Int): Flowable<List<SearchResultEntity>> {
        return searchResultDao
            .getDistinctByWordSearchResults(query, limit)
            .map { it.map { searchResult -> searchResultCacheMapper.mapF(searchResult) } }
    }

    override fun getDistinctByWordFavoriteSearchResults(query: String, limit: Int): Flowable<List<SearchResultEntity>> {
        return searchResultDao
            .getDistinctByWordFavoriteSearchResults(query, limit)
            .map { it.map { searchResult -> searchResultCacheMapper.mapF(searchResult) } }
    }

    override fun getDistinctByWordHistorySearchResults(query: String, limit: Int): Flowable<List<SearchResultEntity>> {
        return searchResultDao
            .getDistinctByWordHistorySearchResults(query, limit)
            .map { it.map { searchResult -> searchResultCacheMapper.mapF(searchResult) } }
    }

    override fun toggleSearchResultInFavorites(id: Int): Completable {
        return Completable.defer {
            searchResultDao.toggleSearchResultInFavorites(id)
            Completable.complete()
        }
    }

    override fun saveSearchResultInHistory(id: Int): Completable {
        return Completable.defer {
            searchResultDao.saveSearchResultInHistory(id)
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
                    it.forEach { fileContent ->
                        val entity = searchResultInstallDataMapper.mapT(fileContent)
                        searchResultDao.insert(entity)
                    }
                }
            }
            Completable.complete()
        }
    }
}
