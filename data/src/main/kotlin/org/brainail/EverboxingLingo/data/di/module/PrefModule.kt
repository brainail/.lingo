package org.brainail.EverboxingLingo.data.di.module

import dagger.Module
import dagger.Provides
import org.brainail.EverboxingLingo.data.settings.AppSettingsImpl
import org.brainail.EverboxingLingo.domain.settings.AppSettings
import javax.inject.Singleton

@Module
internal class PrefModule {
    @Provides
    @Singleton
    fun provideAppPreferences(sharedPreferences: android.content.SharedPreferences): AppSettings = AppSettingsImpl(sharedPreferences)
}