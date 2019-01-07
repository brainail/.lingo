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

package org.brainail.everboxing.lingo.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "search_results",
    indices = [(Index(value = arrayOf("word", "definition"), unique = true))]
)
data class SearchResultCacheEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "sr_id")
    val id: Int,
    @ColumnInfo(name = "definition_id")
    val definitionId: String,
    @ColumnInfo(name = "word")
    val word: String,
    @ColumnInfo(name = "definition")
    val definition: String,
    @ColumnInfo(name = "example")
    val example: String,
    @ColumnInfo(name = "link")
    val link: String,
    @ColumnInfo(name = "favorite")
    val favorite: Boolean,
    @ColumnInfo(name = "history")
    val history: Boolean
)
