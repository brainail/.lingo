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

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import org.brainail.everboxing.lingo.app.App
import org.brainail.everboxing.lingo.domain.event.EventBus
import org.brainail.everboxing.lingo.domain.event.GlobalEvents
import org.brainail.everboxing.lingo.ui.base.AppLifecycleObserver
import org.brainail.everboxing.lingo.util.log.EventBusLogger
import javax.inject.Singleton

@Module(includes = [AppInitializersModule::class])
class AppModule {
    @Provides
    @Singleton
    fun provideApplicationContext(app: App): Context = app.applicationContext

    @Provides
    @Singleton
    fun provideSharedPreferences(app: App): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(app)

    @Provides
    @Singleton
    fun provideAppLifecycleObserver(globalBus: EventBus<GlobalEvents>) = AppLifecycleObserver(globalBus)

    @Provides
    @Singleton
    fun provideGlobalBus(): EventBus<GlobalEvents> = EventBus("global")

    @Provides
    @Singleton
    fun provideEventBusLogger(globalBus: EventBus<GlobalEvents>) = EventBusLogger(globalBus)
}
