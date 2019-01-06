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

package org.brainail.everboxing.lingo.cache.db.dao

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Flowable
import org.brainail.everboxing.lingo.cache.model.SearchResultCacheEntity

@Dao
abstract class SearchResultDao : BaseDao<SearchResultCacheEntity> {
    @Query("select * from search_results where word like :query || '%' order by sr_id desc limit :limit")
    abstract fun getSearchResults(query: String, limit: Int = Int.MAX_VALUE): Flowable<List<SearchResultCacheEntity>>

    @Query(
        """select * from search_results
        where word like :query || '%'
        group by lower(word)
        having max(sr_id)
        order by sr_id desc limit :limit"""
    )
    abstract fun getDistinctByWordSearchResults(
        query: String,
        limit: Int = Int.MAX_VALUE
    ): Flowable<List<SearchResultCacheEntity>>

    @Query("delete from search_results")
    abstract fun deleteAll()

    @Query("update search_results set favorite = 1 - favorite where sr_id = :id")
    abstract fun favoriteSearchResult(id: Int)

    @Query("delete from search_results where sr_id = :id")
    abstract fun forgetSearchResult(id: Int)
}
