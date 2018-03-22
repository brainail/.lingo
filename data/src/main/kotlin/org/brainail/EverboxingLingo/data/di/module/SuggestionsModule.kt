package org.brainail.EverboxingLingo.data.di.module

import dagger.Module
import dagger.Provides
import org.brainail.EverboxingLingo.data.repository.SuggestionsRepositoryImpl
import org.brainail.EverboxingLingo.domain.repository.SuggestionsRepository

@Module
internal class SuggestionsModule {
    @Provides
    fun provideSuggestionsRepository(): SuggestionsRepository = SuggestionsRepositoryImpl()
}