package org.brainail.everboxing.lingo.cache.db

import android.content.Context
import androidx.room.Room

object LingoDatabaseFactory {
    fun makeLingoDatabase(context: Context): LingoDatabase {
        return Room.databaseBuilder(context, LingoDatabase::class.java, "lingo.db")
                .fallbackToDestructiveMigration()
                .build()
    }
}