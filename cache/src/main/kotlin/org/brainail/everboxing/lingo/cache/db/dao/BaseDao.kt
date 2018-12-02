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

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Update

interface BaseDao<T> {
    @Insert(onConflict = IGNORE)
    fun insert(obj: T): Long

    @Insert(onConflict = IGNORE)
    fun insert(vararg obj: T)

    @Insert(onConflict = IGNORE)
    fun insert(obj: List<T>)

    @Insert(onConflict = REPLACE)
    fun insertOrRecreate(obj: List<T>)

    @Update(onConflict = REPLACE)
    fun updateOrRecreate(obj: T): Int

    @Delete
    fun delete(obj: T)
}
