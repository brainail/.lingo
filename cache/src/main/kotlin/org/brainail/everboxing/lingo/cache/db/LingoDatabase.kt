package org.brainail.everboxing.lingo.cache.db

import androidx.room.Database
import androidx.room.RoomDatabase
import org.brainail.everboxing.lingo.cache.db.dao.SearchResultDao
import org.brainail.everboxing.lingo.cache.db.dao.SuggestionDao
import org.brainail.everboxing.lingo.cache.model.SearchResultCacheEntity
import org.brainail.everboxing.lingo.cache.model.SuggestionCacheEntity

@Database(entities = [
    SearchResultCacheEntity::class,
    SuggestionCacheEntity::class
], version = 1, exportSchema = false)
abstract class LingoDatabase : RoomDatabase() {
    abstract fun searchResultDao(): SearchResultDao
    abstract fun suggestionDao(): SuggestionDao
}
