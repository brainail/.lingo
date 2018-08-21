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
    fun update(obj: T): Int
    @Delete
    fun delete(obj: T)
}