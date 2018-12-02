package org.brainail.everboxing.lingo.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
        tableName = "suggestions",
        indices = [(Index(value = arrayOf("word", "description"), unique = true))])
data class SuggestionCacheEntity(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "sg_id")
        val id: Int,
        @ColumnInfo(name = "word")
        val word: String,
        @ColumnInfo(name = "description")
        val description: String,
        @ColumnInfo(name = "is_recent")
        val isRecent: Boolean = false)
