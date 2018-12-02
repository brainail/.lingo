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
