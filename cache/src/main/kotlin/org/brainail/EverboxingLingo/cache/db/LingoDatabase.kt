package org.brainail.EverboxingLingo.cache.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import org.brainail.EverboxingLingo.cache.db.dao.SearchResultDao
import org.brainail.EverboxingLingo.cache.db.dao.SuggestionDao
import org.brainail.EverboxingLingo.cache.model.SearchResultCacheEntity
import org.brainail.EverboxingLingo.cache.model.SuggestionCacheEntity

@Database(entities = [
    SearchResultCacheEntity::class,
    SuggestionCacheEntity::class
], version = 1, exportSchema = false)
abstract class LingoDatabase : RoomDatabase() {
    abstract fun searchResultDao(): SearchResultDao
    abstract fun suggestionDao(): SuggestionDao
}