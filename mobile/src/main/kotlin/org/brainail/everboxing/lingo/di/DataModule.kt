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
            mapper: SuggestionDataMapper): SuggestionsRepository {
        return SuggestionsRepositoryImpl(factory, mapper)
    }

    @Provides
    @Singleton
    fun provideSearchResultRepository(
            appExecutors: AppExecutors,
            factory: SearchResultDataSourceFactory,
            mapper: SearchResultDataMapper): SearchResultRepository {
        return SearchResultRepositoryImpl(factory, mapper, appExecutors)
    }

    @Provides
    @Singleton
    fun provideFileStore(context: Context): FileStore = AndroidFileStore(context)
}