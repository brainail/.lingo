package org.brainail.everboxing.lingo.di

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import org.brainail.everboxing.lingo.app.App
import org.brainail.everboxing.lingo.app.initializers.AppInitializers
import org.brainail.everboxing.lingo.app.initializers.AppLifecycleInitializer
import org.brainail.everboxing.lingo.app.initializers.DataBindingInitializer
import org.brainail.everboxing.lingo.app.initializers.LoggerInitializer
import org.brainail.everboxing.lingo.app.initializers.StethoInitializer
import org.brainail.everboxing.lingo.base.SyncTasks
import org.brainail.everboxing.lingo.domain.event.EventBus
import org.brainail.everboxing.lingo.domain.event.GlobalEvents
import org.brainail.everboxing.lingo.tasks.initializers.PreinstalledUrbanDataInitializer
import org.brainail.everboxing.lingo.ui.base.AppLifecycleObserver
import org.brainail.everboxing.lingo.util.AndroidLogTree
import org.brainail.everboxing.lingo.util.log.EventBusLogger
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
    fun providePreinstalledUrbanDataInitializer(synTasks: SyncTasks)
            = PreinstalledUrbanDataInitializer("preinstalled_popular_words", synTasks)

    @Provides
    @Singleton
    fun provideAppInitializers(
            appLifecycleInitializer: AppLifecycleInitializer,
            loggerInitializer: LoggerInitializer,
            stethoInitializer: StethoInitializer,
            dataBindingInitializer: DataBindingInitializer,
            preinstalledUrbanDataInitializer: PreinstalledUrbanDataInitializer) =
            AppInitializers(appLifecycleInitializer, loggerInitializer,
                    stethoInitializer, dataBindingInitializer, preinstalledUrbanDataInitializer)
}