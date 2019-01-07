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

package org.brainail.everboxing.lingo.data.source.search

import io.reactivex.Completable
import io.reactivex.Flowable
import org.brainail.everboxing.lingo.data.model.SearchResultEntity
import org.brainail.everboxing.lingo.data.repository.search.SearchResultCache
import org.brainail.everboxing.lingo.data.repository.search.SearchResultDataSource
import javax.inject.Inject

class SearchResultCacheDataSource @Inject constructor(
    private val searchResultCache: SearchResultCache
) : SearchResultDataSource {

    override fun saveSearchResults(searchResults: List<SearchResultEntity>): Completable {
        return searchResultCache.saveSearchResults(searchResults)
    }

    override fun getSearchResults(query: String, limit: Int): Flowable<List<SearchResultEntity>> {
        return searchResultCache.getSearchResults(query, limit)
    }

    override fun getFavoriteSearchResults(query: String, limit: Int): Flowable<List<SearchResultEntity>> {
        return searchResultCache.getFavoriteSearchResults(query, limit)
    }

    override fun getHistorySearchResults(query: String, limit: Int): Flowable<List<SearchResultEntity>> {
        return searchResultCache.getHistorySearchResults(query, limit)
    }

    override fun getDistinctByWordSearchResults(query: String, limit: Int): Flowable<List<SearchResultEntity>> {
        return searchResultCache.getDistinctByWordSearchResults(query, limit)
    }

    override fun getDistinctByWordFavoriteSearchResults(query: String, limit: Int): Flowable<List<SearchResultEntity>> {
        return searchResultCache.getDistinctByWordFavoriteSearchResults(query, limit)
    }

    override fun getDistinctByWordHistorySearchResults(query: String, limit: Int): Flowable<List<SearchResultEntity>> {
        return searchResultCache.getDistinctByWordHistorySearchResults(query, limit)
    }

    override fun toggleSearchResultInFavorites(id: Int): Completable {
        return searchResultCache.toggleSearchResultInFavorites(id)
    }

    override fun saveSearchResultInHistory(id: Int): Completable {
        return searchResultCache.saveSearchResultInHistory(id)
    }

    override fun forgetSearchResult(id: Int): Completable {
        return searchResultCache.forgetSearchResult(id)
    }

    override fun installUrbanSearchResult(pathToData: String): Completable {
        return searchResultCache.installUrbanSearchResult(pathToData)
    }
}
