package org.brainail.everboxing.lingo.di

import android.content.Context
import android.content.SharedPreferences
import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import org.brainail.everboxing.lingo.BuildConfig
import org.brainail.everboxing.lingo.cache.SearchResultCacheImpl
import org.brainail.everboxing.lingo.cache.SuggestionCacheImpl
import org.brainail.everboxing.lingo.cache.db.LingoDatabase
import org.brainail.everboxing.lingo.cache.db.LingoDatabaseFactory
import org.brainail.everboxing.lingo.cache.db.dao.SearchResultDao
import org.brainail.everboxing.lingo.cache.db.dao.SuggestionDao
import org.brainail.everboxing.lingo.cache.mapper.SearchResultCacheMapper
import org.brainail.everboxing.lingo.cache.mapper.SuggestionCacheMapper
import org.brainail.everboxing.lingo.data.mapper.SearchResultDataMapper
import org.brainail.everboxing.lingo.data.mapper.SuggestionDataMapper
import org.brainail.everboxing.lingo.data.repository.search.SearchResultCache
import org.brainail.everboxing.lingo.data.repository.search.SearchResultRemote
import org.brainail.everboxing.lingo.data.repository.search.SearchResultRepositoryImpl
import org.brainail.everboxing.lingo.data.repository.suggestion.SuggestionCache
import org.brainail.everboxing.lingo.data.repository.suggestion.SuggestionRemote
import org.brainail.everboxing.lingo.data.repository.suggestion.SuggestionsRepositoryImpl
import org.brainail.everboxing.lingo.data.settings.UserSettingsImpl
import org.brainail.everboxing.lingo.data.source.search.SearchResultDataSourceFactory
import org.brainail.everboxing.lingo.data.source.suggestion.SuggestionsDataSourceFactory
import org.brainail.everboxing.lingo.domain.executor.AppExecutors
import org.brainail.everboxing.lingo.domain.repository.SearchResultRepository
import org.brainail.everboxing.lingo.domain.repository.SuggestionsRepository
import org.brainail.everboxing.lingo.domain.settings.UserSettings
import org.brainail.everboxing.lingo.remote.mapper.UrbanSearchResultRemoteMapper
import org.brainail.everboxing.lingo.remote.mapper.UrbanSuggestionRemoteMapper
import org.brainail.everboxing.lingo.remote.service.urban.UrbanSearchResultRemoteImpl
import org.brainail.everboxing.lingo.remote.service.urban.UrbanService
import org.brainail.everboxing.lingo.remote.service.urban.UrbanServiceFactory
import org.brainail.everboxing.lingo.remote.service.urban.UrbanSuggestionRemoteImpl
import javax.inject.Singleton

@Module
class DataModule {
    @Provides
    @Singleton
    fun provideAppPreferences(sharedPreferences: SharedPreferences): UserSettings = UserSettingsImpl(sharedPreferences)

    @Provides
    @Singleton
    fun provideSuggestionRepository(
            factory: SuggestionsDataSourceFactory,
            mapper: SuggestionDataMapper): SuggestionsRepository {
        return SuggestionsRepositoryImpl(factory, mapper)
    }

    @Provides
    @Singleton
    fun provideSuggestionCache(
            suggestionDao: SuggestionDao,
            entityMapper: SuggestionCacheMapper): SuggestionCache {
        return SuggestionCacheImpl(suggestionDao, entityMapper)
    }

    @Provides
    @Singleton
    fun provideSuggestionRemote(
            service: UrbanService, // TODO: we should able specify others
            entityMapper: UrbanSuggestionRemoteMapper): SuggestionRemote {
        return UrbanSuggestionRemoteImpl(service, entityMapper)
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
    fun provideSearchResultCache(
            searchResultDao: SearchResultDao,
            entityMapper: SearchResultCacheMapper): SearchResultCache {
        return SearchResultCacheImpl(searchResultDao, entityMapper)
    }

    @Provides
    @Singleton
    fun provideSearchResultRemote(
            service: UrbanService, // TODO: we should able specify others
            entityMapper: UrbanSearchResultRemoteMapper): SearchResultRemote {
        return UrbanSearchResultRemoteImpl(service, entityMapper)
    }

    @Provides
    @Singleton
    fun provideUrbanService() = UrbanServiceFactory.makeUrbanService(BuildConfig.DEBUG, StethoInterceptor())

    @Provides
    @Singleton
    fun provideSearchResultDao(lingoDatabase: LingoDatabase) = lingoDatabase.searchResultDao()

    @Provides
    @Singleton
    fun provideSuggestionDao(lingoDatabase: LingoDatabase) = lingoDatabase.suggestionDao()

    @Provides
    @Singleton
    fun provideLingoDatabase(context: Context) = LingoDatabaseFactory.makeLingoDatabase(context)
}