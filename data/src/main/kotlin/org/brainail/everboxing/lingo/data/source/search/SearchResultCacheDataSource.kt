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

    override fun clearSearchResults(): Completable {
        return searchResultCache.clearSearchResults()
    }

    override fun saveSearchResults(searchResults: List<SearchResultEntity>): Completable {
        return searchResultCache.saveSearchResults(searchResults)
    }

    override fun getSearchResults(query: String): Flowable<List<SearchResultEntity>> {
        return searchResultCache.getSearchResults(query)
    }

    override fun favoriteSearchResult(id: Int): Completable {
        return searchResultCache.favoriteSearchResult(id)
    }

    override fun forgetSearchResult(id: Int): Completable {
        return searchResultCache.forgetSearchResult(id)
    }

    override fun installUrbanSearchResult(pathToData: String): Completable {
        return searchResultCache.installUrbanSearchResult(pathToData)
    }
}
