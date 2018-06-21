package org.brainail.EverboxingLingo.cache.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import org.brainail.EverboxingLingo.cache.db.dao.SearchResultDao
import org.brainail.EverboxingLingo.cache.model.SearchResultCacheEntity

@Database(entities = [(SearchResultCacheEntity::class)], version = 1, exportSchema = false)
abstract class LingoDatabase : RoomDatabase() {
    abstract fun searchResultDao(): SearchResultDao
}