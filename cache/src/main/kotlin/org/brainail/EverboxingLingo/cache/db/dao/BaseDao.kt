package org.brainail.EverboxingLingo.cache.db.dao

import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.IGNORE
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Update

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