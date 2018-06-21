package org.brainail.EverboxingLingo.di

import android.content.Context
import android.content.SharedPreferences
import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import org.brainail.EverboxingLingo.BuildConfig
import org.brainail.EverboxingLingo.cache.SearchResultCacheImpl
import org.brainail.EverboxingLingo.cache.SuggestionCacheImpl
import org.brainail.EverboxingLingo.cache.db.LingoDatabase
import org.brainail.EverboxingLingo.cache.db.LingoDatabaseFactory
import org.brainail.EverboxingLingo.cache.db.dao.SearchResultDao
import org.brainail.EverboxingLingo.cache.mapper.SearchResultCacheMapper
import org.brainail.EverboxingLingo.cache.mapper.SuggestionCacheMapper
import org.brainail.EverboxingLingo.data.mapper.SearchResultDataMapper
import org.brainail.EverboxingLingo.data.mapper.SuggestionDataMapper
import org.brainail.EverboxingLingo.data.repository.UserRepositoryImpl
import org.brainail.EverboxingLingo.data.repository.search.SearchResultCache
import org.brainail.EverboxingLingo.data.repository.search.SearchResultRemote
import org.brainail.EverboxingLingo.data.repository.search.SearchResultRepositoryImpl
import org.brainail.EverboxingLingo.data.repository.suggestion.SuggestionCache
import org.brainail.EverboxingLingo.data.repository.suggestion.SuggestionRemote
import org.brainail.EverboxingLingo.data.repository.suggestion.SuggestionsRepositoryImpl
import org.brainail.EverboxingLingo.data.settings.UserSettingsImpl
import org.brainail.EverboxingLingo.data.source.UserPrefDataSource
import org.brainail.EverboxingLingo.data.source.search.SearchResultDataSourceFactory
import org.brainail.EverboxingLingo.data.source.suggestion.SuggestionsDataSourceFactory
import org.brainail.EverboxingLingo.domain.executor.AppExecutors
import org.brainail.EverboxingLingo.domain.repository.SearchResultRepository
import org.brainail.EverboxingLingo.domain.repository.SuggestionsRepository
import org.brainail.EverboxingLingo.domain.repository.UserRepository
import org.brainail.EverboxingLingo.domain.settings.UserSettings
import org.brainail.EverboxingLingo.remote.mapper.UrbanSearchResultRemoteMapper
import org.brainail.EverboxingLingo.remote.mapper.UrbanSuggestionRemoteMapper
import org.brainail.EverboxingLingo.remote.service.urban.UrbanSearchResultRemoteImpl
import org.brainail.EverboxingLingo.remote.service.urban.UrbanService
import org.brainail.EverboxingLingo.remote.service.urban.UrbanServiceFactory
import org.brainail.EverboxingLingo.remote.service.urban.UrbanSuggestionRemoteImpl
import javax.inject.Singleton


@Module
class DataModule {
    @Provides
    @Singleton
    fun provideUserRepository(userPrefDataSource: UserPrefDataSource): UserRepository =
            UserRepositoryImpl(userPrefDataSource)

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
            entityMapper: SuggestionCacheMapper): SuggestionCache {
        return SuggestionCacheImpl(entityMapper)
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
    fun provideLingoDatabase(context: Context) = LingoDatabaseFactory.makeLingoDatabase(context)
}