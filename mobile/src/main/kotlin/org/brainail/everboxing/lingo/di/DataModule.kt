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
import org.brainail.everboxing.lingo.data.mapper.SearchResultDataMapper
import org.brainail.everboxing.lingo.data.mapper.SuggestionDataMapper
import org.brainail.everboxing.lingo.data.repository.file.FileStore
import org.brainail.everboxing.lingo.data.repository.search.SearchResultRepositoryImpl
import org.brainail.everboxing.lingo.data.repository.suggestion.SuggestionsRepositoryImpl
import org.brainail.everboxing.lingo.data.source.search.SearchResultDataSourceFactory
import org.brainail.everboxing.lingo.data.source.suggestion.SuggestionsDataSourceFactory
import org.brainail.everboxing.lingo.domain.executor.AppExecutors
import org.brainail.everboxing.lingo.domain.repository.SearchResultRepository
import org.brainail.everboxing.lingo.domain.repository.SuggestionsRepository
import org.brainail.everboxing.lingo.util.AndroidFileStore
import javax.inject.Singleton

@Module
class DataModule {
    @Provides
    @Singleton
    fun provideSuggestionRepository(
        factory: SuggestionsDataSourceFactory,
        mapper: SuggestionDataMapper
    ): SuggestionsRepository {
        return SuggestionsRepositoryImpl(factory, mapper)
    }

    @Provides
    @Singleton
    fun provideSearchResultRepository(
        appExecutors: AppExecutors,
        factory: SearchResultDataSourceFactory,
        mapper: SearchResultDataMapper
    ): SearchResultRepository {
        return SearchResultRepositoryImpl(factory, mapper, appExecutors)
    }

    @Provides
    @Singleton
    fun provideFileStore(context: Context): FileStore = AndroidFileStore(context)
}
