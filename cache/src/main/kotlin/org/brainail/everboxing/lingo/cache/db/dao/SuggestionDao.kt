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
import androidx.room.Transaction
import io.reactivex.Flowable
import org.brainail.everboxing.lingo.cache.model.SuggestionCacheEntity

@Dao
abstract class SuggestionDao : BaseDao<SuggestionCacheEntity> {
    @Transaction
    open fun saveSuggestions(suggestions: List<SuggestionCacheEntity>) {
        val (recent, new) = suggestions.partition { it.isRecent }
        insert(new)
        insertOrRecreate(recent) // recent items should be always on top
    }

    @Query("select * from suggestions where word like :query || '%' order by sg_id desc limit :limit")
    abstract fun getSuggestions(query: String, limit: Int = Int.MAX_VALUE): Flowable<List<SuggestionCacheEntity>>

    @Query(
        """select * from suggestions
        where is_recent = 1 and word like :query || '%'
        order by sg_id desc limit :limit"""
    )
    abstract fun getRecentSuggestions(query: String, limit: Int = Int.MAX_VALUE): Flowable<List<SuggestionCacheEntity>>

    @Query(
        """select * from suggestions
        where is_recent = 0 and word like :query || '%'
        order by sg_id desc limit :limit"""
    )
    abstract fun getNonRecentSuggestions(
        query: String,
        limit: Int = Int.MAX_VALUE
    ): Flowable<List<SuggestionCacheEntity>>
}
