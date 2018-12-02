package org.brainail.everboxing.lingo.di

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import org.brainail.everboxing.lingo.app.initializers.AppLifecycleInitializer
import org.brainail.everboxing.lingo.app.initializers.DataBindingInitializer
import org.brainail.everboxing.lingo.app.initializers.LoggerInitializer
import org.brainail.everboxing.lingo.app.initializers.StethoInitializer
import org.brainail.everboxing.lingo.base.app.AppInitializer
import org.brainail.everboxing.lingo.tasks.initializers.PreinstalledUrbanDataInitializer

@Module
abstract class AppInitializersModule {
    @Binds
    @IntoSet
    abstract fun bindLoggerInitializer(initializer: LoggerInitializer): AppInitializer

    @Binds
    @IntoSet
    abstract fun bindAppLifecycleInitializer(initializer: AppLifecycleInitializer): AppInitializer

    @Binds
    @IntoSet
    abstract fun bindStethoInitializer(initializer: StethoInitializer): AppInitializer

    @Binds
    @IntoSet
    abstract fun bindDataBindingInitializer(initializer: DataBindingInitializer): AppInitializer

    @Binds
    @IntoSet
    abstract fun bindPreinstalledUrbanDataInitializer(initializer: PreinstalledUrbanDataInitializer): AppInitializer
}
