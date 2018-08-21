package org.brainail.everboxing.lingo.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
        tableName = "search_results",
        indices = [(Index(value = arrayOf("word", "definition"), unique = true))])
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
        val link: String)