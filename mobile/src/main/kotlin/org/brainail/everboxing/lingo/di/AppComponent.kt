package org.brainail.everboxing.lingo.di

import dagger.Component
import dagger.android.AndroidInjector
import org.brainail.everboxing.lingo.app.App
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidModule::class, AppModule::class, ExecutorsModule::class, DataModule::class])
interface AppComponent : AndroidInjector<App> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>()
}