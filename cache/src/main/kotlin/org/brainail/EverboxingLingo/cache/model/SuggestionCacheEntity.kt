package org.brainail.EverboxingLingo.cache.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey

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