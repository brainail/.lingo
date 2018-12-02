/*
 * Copyright 2018 Malyshev Yegor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
    fun provideDatabaseTransactionRunner(lingoDatabase: LingoDatabase): DatabaseTransactionRunner =
        RoomTransactionRunner(lingoDatabase)
}
