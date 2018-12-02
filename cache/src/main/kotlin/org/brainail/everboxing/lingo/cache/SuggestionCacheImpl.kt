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
import org.brainail.everboxing.lingo.cache.db.dao.SuggestionDao
import org.brainail.everboxing.lingo.cache.mapper.SuggestionCacheMapper
import org.brainail.everboxing.lingo.data.model.SuggestionEntity
import org.brainail.everboxing.lingo.data.repository.suggestion.SuggestionCache
import javax.inject.Inject

class SuggestionCacheImpl @Inject constructor(
    private val suggestionDao: SuggestionDao,
    private val entityMapper: SuggestionCacheMapper
) : SuggestionCache {

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
