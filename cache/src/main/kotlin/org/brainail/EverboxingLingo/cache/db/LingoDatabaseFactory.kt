package org.brainail.EverboxingLingo.cache.db

import android.arch.persistence.room.Room
import android.content.Context

object LingoDatabaseFactory {
    fun makeLingoDatabase(context: Context): LingoDatabase {
        return Room.databaseBuilder(context, LingoDatabase::class.java, "lingo.db")
                .fallbackToDestructiveMigration()
                .build()
    }
}