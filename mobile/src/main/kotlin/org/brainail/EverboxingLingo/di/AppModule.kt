package org.brainail.EverboxingLingo.di

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import org.brainail.EverboxingLingo.app.App
import org.brainail.EverboxingLingo.app.initializers.AppInitializers
import org.brainail.EverboxingLingo.app.initializers.AppLifecycleInitializer
import org.brainail.EverboxingLingo.app.initializers.LoggerInitializer
import org.brainail.EverboxingLingo.app.initializers.StethoInitializer
import org.brainail.EverboxingLingo.domain.event.EventBus
import org.brainail.EverboxingLingo.domain.event.GlobalEvents
import org.brainail.EverboxingLingo.ui.base.AppLifecycleObserver
import org.brainail.EverboxingLingo.util.AndroidLogTree
import org.brainail.EverboxingLingo.util.log.EventBusLogger
import org.brainail.logger.L
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    fun provideApplicationContext(app: App): Context = app.applicationContext

    @Provides
    @Singleton
    fun provideSharedPreferences(app: App): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(app)

    @Provides
    @Singleton
    fun provideLogTree(): L.Tree = AndroidLogTree()

    @Provides
    @Singleton
    fun provideAppLifecycleObserver(globalBus: EventBus<GlobalEvents>) = AppLifecycleObserver(globalBus)

    @Provides
    @Singleton
    fun provideGlobalBus(): EventBus<GlobalEvents> = EventBus("global")

    @Provides
    @Singleton
    fun provideEventBusLogger(globalBus: EventBus<GlobalEvents>) = EventBusLogger(globalBus)

    @Provides
    @Singleton
    fun provideAppInitializers(
            appLifecycleInitializer: AppLifecycleInitializer,
            loggerInitializer: LoggerInitializer,
            stethoInitializer: StethoInitializer) =
            AppInitializers(appLifecycleInitializer, loggerInitializer, stethoInitializer)
}