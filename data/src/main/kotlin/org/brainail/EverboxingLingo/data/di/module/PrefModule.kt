package org.brainail.EverboxingLingo.data.di.module

import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import org.brainail.EverboxingLingo.data.settings.UserSettingsImpl
import org.brainail.EverboxingLingo.domain.settings.UserSettings
import javax.inject.Singleton

@Module
internal class PrefModule {
    @Provides
    @Singleton
    fun provideAppPreferences(sharedPreferences: SharedPreferences): UserSettings = UserSettingsImpl(sharedPreferences)
}