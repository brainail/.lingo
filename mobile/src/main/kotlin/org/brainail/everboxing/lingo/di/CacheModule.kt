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

import dagger.Module
import dagger.Provides
import org.brainail.everboxing.lingo.cache.SearchResultCacheImpl
import org.brainail.everboxing.lingo.cache.SuggestionCacheImpl
import org.brainail.everboxing.lingo.cache.db.dao.SearchResultDao
import org.brainail.everboxing.lingo.cache.db.dao.SuggestionDao
import org.brainail.everboxing.lingo.cache.mapper.SearchResultCacheMapper
import org.brainail.everboxing.lingo.cache.mapper.SearchResultInstallDataMapperFactory
import org.brainail.everboxing.lingo.cache.mapper.SuggestionCacheMapper
import org.brainail.everboxing.lingo.data.repository.file.FileStore
import org.brainail.everboxing.lingo.data.repository.search.SearchResultCache
import org.brainail.everboxing.lingo.data.repository.suggestion.SuggestionCache
import org.brainail.everboxing.lingo.data.util.DatabaseTransactionRunner
import javax.inject.Singleton

@Module
class CacheModule {
    @Provides
    @Singleton
    fun provideSuggestionCache(
        suggestionDao: SuggestionDao,
        entityMapper: SuggestionCacheMapper
    ): SuggestionCache {
        return SuggestionCacheImpl(suggestionDao, entityMapper)
    }

    @Provides
    @Singleton
    fun provideSearchResultCache(
        searchResultDao: SearchResultDao,
        fileStore: FileStore,
        searchResultCacheMapper: SearchResultCacheMapper,
        databaseTransactionRunner: DatabaseTransactionRunner
    ): SearchResultCache {
        return SearchResultCacheImpl(
            searchResultDao,
            fileStore,
            SearchResultInstallDataMapperFactory.makeMapper(),
            searchResultCacheMapper,
            databaseTransactionRunner
        )
    }
}
