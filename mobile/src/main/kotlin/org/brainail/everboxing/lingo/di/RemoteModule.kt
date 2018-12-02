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

import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import org.brainail.everboxing.lingo.BuildConfig
import org.brainail.everboxing.lingo.data.repository.search.SearchResultRemote
import org.brainail.everboxing.lingo.data.repository.suggestion.SuggestionRemote
import org.brainail.everboxing.lingo.remote.mapper.UrbanSearchResultRemoteMapper
import org.brainail.everboxing.lingo.remote.mapper.UrbanSuggestionRemoteMapper
import org.brainail.everboxing.lingo.remote.service.urban.UrbanSearchResultRemoteImpl
import org.brainail.everboxing.lingo.remote.service.urban.UrbanService
import org.brainail.everboxing.lingo.remote.service.urban.UrbanServiceFactory
import org.brainail.everboxing.lingo.remote.service.urban.UrbanSuggestionRemoteImpl
import javax.inject.Singleton

@Module
class RemoteModule {
    @Provides
    @Singleton
    fun provideSuggestionRemote(
        service: UrbanService, // TODO: we should able specify others
        entityMapper: UrbanSuggestionRemoteMapper
    ): SuggestionRemote {
        return UrbanSuggestionRemoteImpl(service, entityMapper)
    }

    @Provides
    @Singleton
    fun provideSearchResultRemote(
        service: UrbanService, // TODO: we should able specify others
        entityMapper: UrbanSearchResultRemoteMapper
    ): SearchResultRemote {
        return UrbanSearchResultRemoteImpl(service, entityMapper)
    }

    @Provides
    @Singleton
    fun provideUrbanService() = UrbanServiceFactory.makeUrbanService(BuildConfig.DEBUG, StethoInterceptor())
}
