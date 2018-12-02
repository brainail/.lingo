package org.brainail.everboxing.lingo.di

import dagger.Component
import dagger.android.AndroidInjector
import org.brainail.everboxing.lingo.app.App
import org.brainail.everboxing.lingo.di.tasks.JobsCreator
import org.brainail.everboxing.lingo.tasks.di.AndroidWorkerInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidWorkerInjectionModule::class,
    AndroidModule::class,
    AppModule::class,
    ExecutorsModule::class,
    DataModule::class,
    DatabaseModule::class,
    RemoteModule::class,
    SettingsModule::class,
    CacheModule::class,
    JobsCreator::class])
interface AppComponent : AndroidInjector<App> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>()
}
