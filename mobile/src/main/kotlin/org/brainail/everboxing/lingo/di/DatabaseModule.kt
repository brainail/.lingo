package org.brainail.everboxing.lingo.di

import android.content.Context
import dagger.Module
import dagger.Provides
import org.brainail.everboxing.lingo.cache.db.LingoDatabase
import org.brainail.everboxing.lingo.cache.db.LingoDatabaseFactory
import org.brainail.everboxing.lingo.cache.db.RoomTransactionRunner
import org.brainail.everboxing.lingo.data.util.DatabaseTransactionRunner
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideSearchResultDao(lingoDatabase: LingoDatabase) = lingoDatabase.searchResultDao()

    @Provides
    @Singleton
    fun provideSuggestionDao(lingoDatabase: LingoDatabase) = lingoDatabase.suggestionDao()

    @Provides
    @Singleton
    fun provideLingoDatabase(context: Context) = LingoDatabaseFactory.makeLingoDatabase(context)

    @Singleton
    @Provides
    fun provideDatabaseTransactionRunner(lingoDatabase: LingoDatabase): DatabaseTransactionRunner
            = RoomTransactionRunner(lingoDatabase)
}