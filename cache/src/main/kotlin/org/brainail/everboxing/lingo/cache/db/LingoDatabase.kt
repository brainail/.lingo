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

package org.brainail.everboxing.lingo.cache.db

import androidx.room.Database
import androidx.room.RoomDatabase
import org.brainail.everboxing.lingo.cache.db.dao.SearchResultDao
import org.brainail.everboxing.lingo.cache.db.dao.SuggestionDao
import org.brainail.everboxing.lingo.cache.model.SearchResultCacheEntity
import org.brainail.everboxing.lingo.cache.model.SuggestionCacheEntity

@Database(
    entities = [
        SearchResultCacheEntity::class,
        SuggestionCacheEntity::class
    ], version = 1, exportSchema = false
)
abstract class LingoDatabase : RoomDatabase() {
    abstract fun searchResultDao(): SearchResultDao
    abstract fun suggestionDao(): SuggestionDao
}
