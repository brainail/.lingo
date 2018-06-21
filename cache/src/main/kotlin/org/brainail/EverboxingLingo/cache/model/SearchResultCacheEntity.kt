package org.brainail.EverboxingLingo.cache.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey

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