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
import org.brainail.everboxing.lingo.data.util.DatabaseTransactionRunner
import org.brainail.everboxing.lingo.data.repository.file.FileStore
import org.brainail.everboxing.lingo.data.repository.search.SearchResultCache
import org.brainail.everboxing.lingo.data.repository.suggestion.SuggestionCache
import javax.inject.Singleton

@Module
class CacheModule {
    @Provides
    @Singleton
    fun provideSuggestionCache(
            suggestionDao: SuggestionDao,
            entityMapper: SuggestionCacheMapper): SuggestionCache {
        return SuggestionCacheImpl(suggestionDao, entityMapper)
    }

    @Provides
    @Singleton
    fun provideSearchResultCache(
            searchResultDao: SearchResultDao,
            fileStore: FileStore,
            searchResultCacheMapper: SearchResultCacheMapper,
            databaseTransactionRunner: DatabaseTransactionRunner): SearchResultCache {
        return SearchResultCacheImpl(searchResultDao, fileStore,
                SearchResultInstallDataMapperFactory.makeMapper(),
                searchResultCacheMapper,
                databaseTransactionRunner)
    }
}