package org.brainail.everboxing.lingo.di

import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import org.brainail.everboxing.lingo.data.settings.SyncSettingsImpl
import org.brainail.everboxing.lingo.data.settings.UserSettingsImpl
import org.brainail.everboxing.lingo.domain.settings.SyncSettings
import org.brainail.everboxing.lingo.domain.settings.UserSettings
import javax.inject.Singleton

@Module
class SettingsModule {
    @Provides
    @Singleton
    fun provideUserSettings(sharedPreferences: SharedPreferences): UserSettings = UserSettingsImpl(sharedPreferences)

    @Provides
    @Singleton
    fun provideSyncSettings(sharedPreferences: SharedPreferences): SyncSettings = SyncSettingsImpl(sharedPreferences)
}
