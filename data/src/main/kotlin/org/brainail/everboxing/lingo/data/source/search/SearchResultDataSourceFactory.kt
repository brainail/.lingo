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

import org.brainail.everboxing.lingo.data.repository.search.SearchResultCache
import org.brainail.everboxing.lingo.data.repository.search.SearchResultDataSource
import javax.inject.Inject

class SearchResultDataSourceFactory @Inject constructor(
    private val searchResultCache: SearchResultCache,
    private val searchResultCacheDataSource: SearchResultCacheDataSource,
    private val searchResultRemoteDataSource: SearchResultRemoteDataSource
) {

    fun obtainDataSource(): SearchResultDataSource {
        if (searchResultCache.isCached() && !searchResultCache.isExpired()) {
            return obtainCacheDataSource()
        }
        return obtainRemoteDataSource()
    }

    fun obtainCacheDataSource(): SearchResultDataSource {
        return searchResultCacheDataSource
    }

    fun obtainRemoteDataSource(): SearchResultDataSource {
        return searchResultRemoteDataSource
    }
}
