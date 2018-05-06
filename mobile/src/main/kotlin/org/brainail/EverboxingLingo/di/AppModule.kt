package org.brainail.EverboxingLingo.di

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import org.brainail.EverboxingLingo.App
import org.brainail.EverboxingLingo.AppNavigator
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
    fun provideApplicationContext(app: App): Context {
        return app.applicationContext
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(app: App): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(app)

    @Provides
    @Singleton
    fun provideLogTree(): L.Tree = AndroidLogTree()

    @Provides
    @Singleton
    fun provideAppLifecycleObserver(globalBus: EventBus<GlobalEvents>): AppLifecycleObserver
            = AppLifecycleObserver(globalBus)

    @Provides
    @Singleton
    fun provideGlobalBus(): EventBus<GlobalEvents> = EventBus("global")

    @Provides
    @Singleton
    fun provideEventBusLogger(globalBus: EventBus<GlobalEvents>): EventBusLogger
            = EventBusLogger(globalBus)

    @Provides
    @Singleton
    fun provideAppNavigator(context: Context): AppNavigator = AppNavigator(context)
}